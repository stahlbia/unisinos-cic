# AWS SNS Setup Guide

This guide explains how to configure the Tech Newsletter Service to use real AWS SNS for email delivery.

## Overview

The service now supports **multiple SNS topics** - one for each newsletter category. This provides better organization and allows subscribers to be managed separately for each topic.

## Step 1: Configure AWS Credentials

### Option A: Environment Variables (Recommended for Development)

Add these to your `.env` file:

```bash
AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=your_access_key_here
AWS_SECRET_ACCESS_KEY=your_secret_key_here
AWS_ACCOUNT_ID=your_account_id_here
```

### Option B: AWS CLI (Alternative)

```bash
aws configure
# Follow prompts to enter your credentials
```

### Option C: IAM Roles (Recommended for Production)

If running on EC2, use IAM roles instead of hardcoded credentials.

## Step 2: Create SNS Topics

Create separate SNS topics for each newsletter category:

```bash
# Create topics for each category
aws sns create-topic --name tech-newsletter-ai --region us-east-1
aws sns create-topic --name tech-newsletter-backend --region us-east-1
aws sns create-topic --name tech-newsletter-devops --region us-east-1
aws sns create-topic --name tech-newsletter-frontend --region us-east-1
aws sns create-topic --name tech-newsletter-mobile --region us-east-1
```

**Note the Topic ARNs** returned by each command. They look like:

```
arn:aws:sns:us-east-1:123456789012:tech-newsletter-ai
```

## Step 3: Configure Topic ARNs

Add the specific topic ARNs to your `.env` file:

```bash
# SNS Topic ARNs for each newsletter topic
SNS_TOPIC_ARN_AI=arn:aws:sns:us-east-1:123456789012:tech-newsletter-ai
SNS_TOPIC_ARN_BACKEND=arn:aws:sns:us-east-1:123456789012:tech-newsletter-backend
SNS_TOPIC_ARN_DEVOPS=arn:aws:sns:us-east-1:123456789012:tech-newsletter-devops
SNS_TOPIC_ARN_FRONTEND=arn:aws:sns:us-east-1:123456789012:tech-newsletter-frontend
SNS_TOPIC_ARN_MOBILE=arn:aws:sns:us-east-1:123456789012:tech-newsletter-mobile
```

## Step 4: Subscribe Emails to SNS Topics

### Option A: Manual Subscription (for testing)

```bash
# Subscribe an email to the AI topic
aws sns subscribe \
  --topic-arn arn:aws:sns:us-east-1:123456789012:tech-newsletter-ai \
  --protocol email \
  --notification-endpoint test@example.com
```

The subscriber will receive a confirmation email they must click to confirm.

### Option B: Automatic Subscription (Recommended)

The service can automatically subscribe users to SNS topics when they subscribe via the API. This requires additional implementation:

```javascript
// Example: Auto-subscribe users to SNS when they subscribe via API
app.post('/api/subscribe', async (req, res) => {
  const { email, topics } = req.body;
  
  // Store subscription in database
  await db.subscribeUser(email, topics);
  
  // Also subscribe to SNS topics
  for (const topic of topics) {
    const topicArn = newsletterService.getTopicArn(topic);
    try {
      await newsletterService.subscribeEmailToSNS(topicArn, email);
    } catch (error) {
      console.warn(`Failed to subscribe ${email} to SNS topic ${topic}:`, error);
    }
  }
  
  res.json({ message: 'Subscribed successfully' });
});
```

## Step 5: Test the Configuration

### Check SNS Topic Configuration

```bash
curl http://localhost:3000/api/admin/sns-topics
```

This will show you which topics are properly configured.

### Send a Test Newsletter

```bash
curl -X POST http://localhost:3000/api/admin/send-newsletter \
  -H "Content-Type: application/json" \
  -d '{
    "topic": "ai",
    "subject": "Test Newsletter",
    "content": "This is a test of the AWS SNS integration!"
  }'
```

## How It Works

### Development Mode

- When SNS is not configured, emails are simulated and logged to console
- Perfect for development and testing

### Production Mode  

- When proper SNS_TOPIC_ARN_* variables are set, real emails are sent
- Each topic uses its own SNS topic for better organization
- AWS handles the actual email delivery

### Email Flow

1. **User subscribes** → Stored in local database + optionally added to SNS topic
2. **Admin sends newsletter** → Service publishes to appropriate SNS topic
3. **AWS SNS delivers** → Email sent to all subscribers of that topic

## Required AWS Permissions

Your AWS user/role needs these permissions:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "sns:Publish",
        "sns:Subscribe",
        "sns:Unsubscribe",
        "sns:ListSubscriptionsByTopic",
        "sns:GetTopicAttributes",
        "sns:CreateTopic"
      ],
      "Resource": "arn:aws:sns:*:*:tech-newsletter-*"
    }
  ]
}
```

## Monitoring and Debugging

### Check Service Status

```bash
curl http://localhost:3000/health
```

### View Metrics

```bash
curl http://localhost:3000/api/metrics
```

### Check AWS SNS Console

- Log into AWS Console → SNS
- View your topics and their subscriptions
- Check delivery status and failed deliveries

## Troubleshooting

### Newsletter Shows as "Simulated"

- Check that SNS_TOPIC_ARN_* variables are set correctly
- Verify AWS credentials are configured
- Ensure NODE_ENV is not set to 'development' in production

### Permission Denied Errors

- Verify AWS credentials have SNS permissions
- Check that the topic ARNs are correct
- Ensure the AWS region matches your configuration

### Emails Not Delivered

- Check AWS SNS console for delivery failures
- Verify subscribers have confirmed their email subscriptions
- Check your AWS account's SNS sending limits

## Cost Considerations

- SNS pricing: ~$0.50 per 1 million email notifications
- No setup costs or monthly fees
- First 1,000 email notifications per month are free
- Very cost-effective for newsletter services

## Security Best Practices

- Use IAM roles instead of hardcoded credentials in production
- Set up AWS CloudTrail to monitor SNS API calls
- Implement rate limiting to prevent abuse
- Consider adding authentication to admin endpoints
- Never commit AWS credentials to version control
