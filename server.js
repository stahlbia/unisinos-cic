// Load environment variables from .env file
require('dotenv').config();

const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const helmet = require('helmet');
const AWS = require('aws-sdk');
const Database = require('./database');
const NewsletterService = require('./services/newsletter-service');
const MetricsService = require('./services/metrics-service');
const { validateEmail, validateTopic, validateNewsletterContent } = require('./utils/validation');

const app = express();
const PORT = process.env.PORT || 3000;

// Initialize services
const db = new Database();
const newsletterService = new NewsletterService();
const metricsService = new MetricsService();

// Middleware
app.use(helmet());
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Middleware to track metrics
app.use((req, res, next) => {
  metricsService.trackRequest(req.method, req.path);
  next();
});

// Health check endpoint
app.get('/health', (req, res) => {
  res.json({ 
    status: 'healthy', 
    timestamp: new Date().toISOString(),
    service: 'tech-newsletter-service'
  });
});

// Get all available topics
app.get('/api/topics', (req, res) => {
  try {
    const topics = newsletterService.getAvailableTopics();
    res.json({ topics });
  } catch (error) {
    console.error('Error fetching topics:', error);
    res.status(500).json({ error: 'Failed to fetch topics' });
  }
});

// Subscribe user to topics
app.post('/api/subscribe', async (req, res) => {
  try {
    const { email, topics } = req.body;
    
    // Validate input
    const emailValidation = validateEmail(email);
    if (emailValidation.error) {
      return res.status(400).json({ error: emailValidation.error.details[0].message });
    }

    if (!Array.isArray(topics) || topics.length === 0) {
      return res.status(400).json({ error: 'Topics array is required and cannot be empty' });
    }

    // Validate each topic
    for (const topic of topics) {
      const topicValidation = validateTopic(topic);
      if (topicValidation.error) {
        return res.status(400).json({ error: `Invalid topic: ${topic}` });
      }
    }

    // Subscribe user to database
    const result = await db.subscribeUser(email, topics);
    
    // Subscribe user to actual AWS SNS topics
    const snsSubscriptions = [];
    const snsErrors = [];
    
    for (const topic of topics) {
      try {
        const topicArn = newsletterService.getTopicArn(topic);
        
        if (newsletterService.hasValidTopicArn(topic)) {
          const snsSubscription = await newsletterService.subscribeEmailToSNS(topicArn, email);
          snsSubscriptions.push({
            topic,
            topicArn,
            subscriptionArn: snsSubscription,
            status: 'subscribed'
          });
          console.log(`Successfully subscribed ${email} to SNS topic ${topic}`);
        } else {
          snsSubscriptions.push({
            topic,
            topicArn,
            status: 'simulated',
            note: 'SNS not configured - subscription simulated'
          });
          console.log(`SNS not configured for topic ${topic} - subscription simulated for ${email}`);
        }
      } catch (error) {
        console.error(`Failed to subscribe ${email} to SNS topic ${topic}:`, error);
        snsErrors.push({
          topic,
          error: error.message
        });
        // Continue with other topics even if one fails
      }
    }
    
    metricsService.trackSubscription(topics);
    
    const response = { 
      message: 'Successfully subscribed to topics',
      subscriptionId: result.subscriptionId,
      topics: topics,
      snsSubscriptions,
      status: snsErrors.length === 0 ? 'success' : 'partial_success'
    };
    
    if (snsErrors.length > 0) {
      response.snsErrors = snsErrors;
      response.warning = 'Some SNS subscriptions failed but database subscription succeeded';
    }
    
    res.json(response);
  } catch (error) {
    console.error('Error subscribing user:', error);
    res.status(500).json({ error: 'Failed to subscribe user' });
  }
});

// Unsubscribe user from topics
app.post('/api/unsubscribe', async (req, res) => {
  try {
    const { email, topics } = req.body;
    
    const emailValidation = validateEmail(email);
    if (emailValidation.error) {
      return res.status(400).json({ error: emailValidation.error.details[0].message });
    }

    // Unsubscribe from database
    const result = await db.unsubscribeUser(email, topics);
    
    // Note: AWS SNS unsubscription is more complex as it requires subscription ARNs
    // For now, we'll track the unsubscription but SNS cleanup would need to be done
    // through AWS console or by storing subscription ARNs in the database
    const response = {
      message: topics ? 'Successfully unsubscribed from specified topics' : 'Successfully unsubscribed from all topics',
      email,
      topics: topics || 'all',
      note: 'Database unsubscription completed. SNS subscriptions may need manual cleanup via AWS console.'
    };
    
    metricsService.trackUnsubscription(topics || []);
    
    res.json(response);
  } catch (error) {
    console.error('Error unsubscribing user:', error);
    res.status(500).json({ error: 'Failed to unsubscribe user' });
  }
});

// Get user subscriptions
app.get('/api/subscriptions/:email', async (req, res) => {
  try {
    const { email } = req.params;
    
    const emailValidation = validateEmail(email);
    if (emailValidation.error) {
      return res.status(400).json({ error: emailValidation.error.details[0].message });
    }

    const subscriptions = await db.getUserSubscriptions(email);
    res.json({ email, topics: subscriptions });
  } catch (error) {
    console.error('Error fetching user subscriptions:', error);
    res.status(500).json({ error: 'Failed to fetch subscriptions' });
  }
});

// Admin endpoint to send newsletter
app.post('/api/admin/send-newsletter', async (req, res) => {
  try {
    const { topics, subject, content } = req.body;
    
    // Support both single topic (backward compatibility) and multiple topics
    const topicList = topics ? (Array.isArray(topics) ? topics : [topics]) 
                             : (req.body.topic ? [req.body.topic] : null);
    
    // Validate input
    if (!topicList || !subject || !content) {
      return res.status(400).json({ error: 'Topics (or topic), subject, and content are required' });
    }

    // Validate each topic
    for (const topic of topicList) {
      const topicValidation = validateTopic(topic);
      if (topicValidation.error) {
        return res.status(400).json({ error: `Invalid topic: ${topic}` });
      }
    }

    if (subject.trim().length < 1 || subject.length > 200) {
      return res.status(400).json({ error: 'Subject must be between 1 and 200 characters' });
    }

    if (content.trim().length < 10 || content.length > 10000) {
      return res.status(400).json({ error: 'Content must be between 10 and 10,000 characters' });
    }

    // Get all subscribers for the specified topics (avoiding duplicates)
    const allSubscribers = new Set();
    const topicSubscriberCounts = {};
    
    for (const topic of topicList) {
      const subscribers = await db.getSubscribersByTopic(topic);
      topicSubscriberCounts[topic] = subscribers.length;
      subscribers.forEach(email => allSubscribers.add(email));
    }
    
    const uniqueSubscribers = Array.from(allSubscribers);
    
    if (uniqueSubscribers.length === 0) {
      return res.json({ 
        message: 'No subscribers found for the specified topics',
        topics: topicList,
        topicSubscriberCounts,
        uniqueSubscriberCount: 0
      });
    }

    // Send newsletter (AWS SNS handles sender info)
    const result = await newsletterService.sendNewsletter({
      topics: topicList,
      subject,
      content,
      subscribers: uniqueSubscribers
    });

    // Track metrics for each topic
    topicList.forEach(topic => {
      metricsService.trackNewsletterSent(topic, topicSubscriberCounts[topic]);
    });
    
    res.json({
      message: 'Newsletter sent successfully',
      topics: topicList,
      topicSubscriberCounts,
      uniqueSubscriberCount: uniqueSubscribers.length,
      result
    });
  } catch (error) {
    console.error('Error sending newsletter:', error);
    metricsService.trackError('newsletter_send_failed');
    res.status(500).json({ error: 'Failed to send newsletter' });
  }
});

// Get service metrics
app.get('/api/metrics', async (req, res) => {
  try {
    const hours = parseInt(req.query.hours) || null; // Allow filtering by hours, default to all
    
    // Get in-memory metrics (current session)
    const currentSessionMetrics = metricsService.getMetrics();
    
    // Get database metrics (all historical data)
    const databaseMetrics = await metricsService.getDatabaseMetrics(hours);
    
    // Combine both in-memory and database metrics
    const combinedMetrics = {
      current_session: {
        ...currentSessionMetrics,
        note: 'Metrics since last server restart'
      },
      historical: databaseMetrics,
      combined_summary: {
        note: hours ? 
          `Combined metrics for the last ${hours} hours` : 
          'Combined metrics for all time',
        timestamp: new Date().toISOString()
      }
    };
    
    res.json(combinedMetrics);
  } catch (error) {
    console.error('Error fetching metrics:', error);
    res.status(500).json({ error: 'Failed to fetch metrics' });
  }
});

// Get subscribers count by topic
app.get('/api/admin/subscribers/count', async (req, res) => {
  try {
    const counts = await db.getSubscriberCounts();
    res.json(counts);
  } catch (error) {
    console.error('Error fetching subscriber counts:', error);
    res.status(500).json({ error: 'Failed to fetch subscriber counts' });
  }
});

// Get configured SNS topics
app.get('/api/admin/sns-topics', (req, res) => {
  try {
    const topicArns = newsletterService.getAllTopicArns();
    const topicConfig = {
      topics: topicArns,
      configured: Object.entries(topicArns).map(([topic, arn]) => ({
        topic,
        arn,
        isConfigured: newsletterService.hasValidTopicArn(topic),
        envVar: `SNS_TOPIC_ARN_${topic.toUpperCase()}`
      }))
    };
    res.json(topicConfig);
  } catch (error) {
    console.error('Error fetching SNS topic configuration:', error);
    res.status(500).json({ error: 'Failed to fetch SNS topic configuration' });
  }
});

// Error handling middleware
app.use((error, req, res, next) => {
  console.error('Unhandled error:', error);
  metricsService.trackError('unhandled_error');
  res.status(500).json({ error: 'Internal server error' });
});

// 404 handler
app.use((req, res) => {
  res.status(404).json({ error: 'Endpoint not found' });
});

// Initialize database and start server
async function startServer() {
  try {
    await db.initialize();
    console.log('Database initialized successfully');
    
    // Initialize metrics service
    await metricsService.initialize();
    console.log('Metrics service initialized successfully');
    
    app.listen(PORT, () => {
      console.log(`Tech Newsletter Service running on port ${PORT}`);
      console.log(`Health check: http://localhost:${PORT}/health`);
      console.log(`Topics: http://localhost:${PORT}/api/topics`);
      console.log(`Metrics: http://localhost:${PORT}/api/metrics`);
    });
  } catch (error) {
    console.error('Failed to start server:', error);
    process.exit(1);
  }
}

startServer();

module.exports = app;
