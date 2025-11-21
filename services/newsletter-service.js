const AWS = require('aws-sdk');

class NewsletterService {
  constructor() {
    // Configure AWS SNS
    this.sns = new AWS.SNS({
      region: process.env.AWS_REGION || 'us-east-1',
      // AWS credentials should be configured via environment variables:
      // AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
      // or via IAM roles if running on EC2
    });

    // Available topics for the tech newsletter
    this.availableTopics = ['ai', 'backend', 'devops', 'frontend', 'mobile'];
  }

  getAvailableTopics() {
    return this.availableTopics;
  }

  isValidTopic(topic) {
    return this.availableTopics.includes(topic.toLowerCase());
  }

  async sendNewsletter({ topic, subject, content, subscribers }) {
    try {
      // Validate topic
      if (!this.isValidTopic(topic)) {
        throw new Error(`Invalid topic: ${topic}. Available topics: ${this.availableTopics.join(', ')}`);
      }

      // Create the email message
      const message = this.formatEmailMessage(topic, subject, content);

      // SNS message parameters
      const params = {
        Message: message,
        Subject: `${this.getTopicDisplayName(topic)} Newsletter: ${subject}`,
        MessageAttributes: {
          'topic': {
            DataType: 'String',
            StringValue: topic
          },
          'subscriber_count': {
            DataType: 'Number',
            StringValue: subscribers.length.toString()
          }
        }
      };

      // Get the appropriate SNS topic ARN for this newsletter topic
      const topicArn = this.getTopicArn(topic);
      
      if (process.env.NODE_ENV === 'development' || !this.hasValidTopicArn(topic)) {
        // In development mode or when SNS is not configured, simulate sending
        console.log('DEVELOPMENT MODE - Newsletter would be sent with:');
        console.log('Topic:', topic);
        console.log('Topic ARN:', topicArn);
        console.log('Subject:', params.Subject);
        console.log('Subscribers count:', subscribers.length);
        console.log('Message preview:', message.substring(0, 200) + '...');
        
        return {
          messageId: `dev-${Date.now()}`,
          topic,
          subscriberCount: subscribers.length,
          status: 'simulated',
          topicArn
        };
      }

      // Send to appropriate SNS topic - AWS will handle email delivery to all subscribers
      params.TopicArn = topicArn;
      const result = await this.sns.publish(params).promise();

      console.log(`Newsletter sent successfully for topic ${topic}:`, result.MessageId);
      console.log(`Delivered to ${subscribers.length} subscribers via SNS topic: ${topicArn}`);
      
      return {
        messageId: result.MessageId,
        topic,
        subscriberCount: subscribers.length,
        status: 'sent',
        topicArn
      };

    } catch (error) {
      console.error('Error sending newsletter:', error);
      
      // If SNS is not configured, simulate the sending
      if (error.code === 'CredentialsError' || error.code === 'ConfigError') {
        console.log('AWS SNS not configured - simulating newsletter send');
        return {
          messageId: `sim-${Date.now()}`,
          topic,
          subscriberCount: subscribers.length,
          status: 'simulated',
          note: 'AWS SNS not configured - newsletter sending simulated'
        };
      }
      
      throw error;
    }
  }

  getTopicArn(topic) {
    // Try to get topic-specific ARN from environment variables
    const topicEnvVar = `SNS_TOPIC_ARN_${topic.toUpperCase()}`;
    const specificTopicArn = process.env[topicEnvVar];
    
    if (specificTopicArn) {
      return specificTopicArn;
    }

    // Fallback to legacy single topic ARN (deprecated)
    if (process.env.SNS_TOPIC_ARN) {
      console.warn(`Using deprecated SNS_TOPIC_ARN for topic ${topic}. Consider using ${topicEnvVar} instead.`);
      return process.env.SNS_TOPIC_ARN;
    }

    // Generate ARN based on AWS account info
    const region = process.env.AWS_REGION || 'us-east-1';
    const accountId = process.env.AWS_ACCOUNT_ID;
    
    if (accountId) {
      return `arn:aws:sns:${region}:${accountId}:tech-newsletter-${topic}`;
    }

    // Return a placeholder ARN for development
    return `arn:aws:sns:${region}:123456789012:tech-newsletter-${topic}`;
  }

  hasValidTopicArn(topic) {
    const topicEnvVar = `SNS_TOPIC_ARN_${topic.toUpperCase()}`;
    return !!(process.env[topicEnvVar] || process.env.SNS_TOPIC_ARN || process.env.AWS_ACCOUNT_ID);
  }

  getAllTopicArns() {
    const arns = {};
    this.availableTopics.forEach(topic => {
      arns[topic] = this.getTopicArn(topic);
    });
    return arns;
  }

  formatEmailMessage(topic, subject, content) {
    const topicDisplayName = this.getTopicDisplayName(topic);
    
    return `
════════════════════════════════════════
🚀 ${topicDisplayName.toUpperCase()} TECH NEWSLETTER
════════════════════════════════════════

${subject}

${content}

────────────────────────────────────────

📅 Date: ${new Date().toLocaleDateString()}
🏷️  Topic: ${topicDisplayName}

────────────────────────────────────────

To unsubscribe from ${topicDisplayName} newsletters, reply with "UNSUBSCRIBE ${topic.toUpperCase()}"
To unsubscribe from all newsletters, reply with "UNSUBSCRIBE ALL"

Tech Newsletter Service - Keeping you updated with the latest in technology!
    `.trim();
  }

  getTopicDisplayName(topic) {
    const displayNames = {
      'ai': 'Artificial Intelligence',
      'backend': 'Backend Development',
      'devops': 'DevOps & Infrastructure',
      'frontend': 'Frontend Development',
      'mobile': 'Mobile Development'
    };
    
    return displayNames[topic.toLowerCase()] || topic;
  }

  async createSNSTopic(topic) {
    try {
      const params = {
        Name: `tech-newsletter-${topic}`,
        DisplayName: `Tech Newsletter - ${this.getTopicDisplayName(topic)}`
      };

      const result = await this.sns.createTopic(params).promise();
      console.log(`SNS Topic created: ${result.TopicArn}`);
      return result.TopicArn;
    } catch (error) {
      console.error('Error creating SNS topic:', error);
      throw error;
    }
  }

  async subscribeEmailToSNS(topicArn, email) {
    try {
      const params = {
        Protocol: 'email',
        TopicArn: topicArn,
        Endpoint: email
      };

      const result = await this.sns.subscribe(params).promise();
      console.log(`Email ${email} subscribed to ${topicArn}`);
      return result.SubscriptionArn;
    } catch (error) {
      console.error('Error subscribing email to SNS:', error);
      throw error;
    }
  }

  async getTopicAttributes(topicArn) {
    try {
      const params = {
        TopicArn: topicArn
      };

      const result = await this.sns.getTopicAttributes(params).promise();
      return result.Attributes;
    } catch (error) {
      console.error('Error getting topic attributes:', error);
      throw error;
    }
  }
}

module.exports = NewsletterService;
