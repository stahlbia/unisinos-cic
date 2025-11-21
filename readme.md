# Tech Newsletter Service

A simple REST API service for managing tech newsletter subscriptions and sending emails via AWS SNS. Users can subscribe to different tech topics (AI, Backend, DevOps, Frontend, Mobile) and admins can send newsletters to subscribers.

## Features

- ✅ **Topic-based subscriptions**: Users can subscribe to specific tech topics
- ✅ **AWS SNS integration**: Email delivery through Amazon Simple Notification Service
- ✅ **REST API**: Simple HTTP endpoints for all operations
- ✅ **SQLite database**: Lightweight database for storing subscriptions
- ✅ **Metrics tracking**: Built-in analytics and monitoring
- ✅ **Input validation**: Comprehensive validation for all inputs
- ✅ **Error handling**: Robust error handling and logging
- ✅ **Development mode**: Simulates email sending when AWS is not configured

## Available Topics

- `ai` - Artificial Intelligence
- `backend` - Backend Development
- `devops` - DevOps & Infrastructure
- `frontend` - Frontend Development
- `mobile` - Mobile Development

## Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- AWS account (optional for development)

## Installation

1. Clone or download this project
2. Navigate to the project directory:

   ```bash
   cd tech-newsletter
   ```

3. Install dependencies:

   ```bash
   npm install
   ```

4. Copy the environment file:

   ```bash
   cp .env.example .env
   ```

5. Configure your environment variables in `.env` (optional for development):

   ```bash
   # AWS Configuration (optional - service will simulate emails if not configured)
   AWS_REGION=us-east-1
   AWS_ACCESS_KEY_ID=your_access_key_here
   AWS_SECRET_ACCESS_KEY=your_secret_key_here
   AWS_ACCOUNT_ID=your_account_id_here
   
   # SNS Topic ARNs for each newsletter topic (recommended)
   SNS_TOPIC_ARN_AI=arn:aws:sns:us-east-1:your_account_id:tech-newsletter-ai
   SNS_TOPIC_ARN_BACKEND=arn:aws:sns:us-east-1:your_account_id:tech-newsletter-backend
   SNS_TOPIC_ARN_DEVOPS=arn:aws:sns:us-east-1:your_account_id:tech-newsletter-devops
   SNS_TOPIC_ARN_FRONTEND=arn:aws:sns:us-east-1:your_account_id:tech-newsletter-frontend
   SNS_TOPIC_ARN_MOBILE=arn:aws:sns:us-east-1:your_account_id:tech-newsletter-mobile
   ```

## Running the Service

### Development Mode

```bash
npm run dev
```

### Production Mode

```bash
npm start
```

The service will start on `http://localhost:3000` (or the port specified in your `.env` file).

## API Endpoints

### Health Check

```http
GET /health
```

Returns service health status.

### Topics

```http
GET /api/topics
```

Get all available newsletter topics.

### Subscriptions

#### Subscribe to Topics

```http
POST /api/subscribe
Content-Type: application/json

{
  "email": "user@example.com",
  "topics": ["ai", "backend", "frontend"]
}
```

#### Get User Subscriptions

```http
GET /api/subscriptions/user@example.com
```

#### Unsubscribe from Topics

```http
POST /api/unsubscribe
Content-Type: application/json

{
  "email": "user@example.com",
  "topics": ["ai"]  // Optional: omit to unsubscribe from all
}
```

### Admin Endpoints

#### Send Newsletter

```http
POST /api/admin/send-newsletter
Content-Type: application/json

{
  "topic": "ai",
  "subject": "Latest AI Developments",
  "content": "Here are the latest updates in AI technology..."
}
```

#### Get Subscriber Counts

```http
GET /api/admin/subscribers/count
```

#### Get SNS Topic Configuration

```http
GET /api/admin/sns-topics
```

Get information about configured SNS topics and their ARNs.

### Metrics

```http
GET /api/metrics
```

Get service metrics including requests, subscriptions, and error rates.

## Example Usage

### 1. Subscribe a User

```bash
curl -X POST http://localhost:3000/api/subscribe \
  -H "Content-Type: application/json" \
  -d '{
    "email": "developer@example.com",
    "topics": ["ai", "backend"]
  }'
```

### 2. Send Newsletter

```bash
curl -X POST http://localhost:3000/api/admin/send-newsletter \
  -H "Content-Type: application/json" \
  -d '{
    "topic": "ai",
    "subject": "Weekly AI Update",
    "content": "This week in AI: GPT-4 improvements, new ML frameworks, and more!"
  }'
```

### 3. Check Metrics

```bash
curl http://localhost:3000/api/metrics
```

## AWS SNS Setup (Optional)

For production use with actual email delivery:

1. Create an AWS account and configure credentials
2. Create SNS topics for each newsletter category:

   ```bash
   aws sns create-topic --name tech-newsletter-ai
   aws sns create-topic --name tech-newsletter-backend
   aws sns create-topic --name tech-newsletter-devops
   aws sns create-topic --name tech-newsletter-frontend
   aws sns create-topic --name tech-newsletter-mobile
   ```

3. Set up email endpoints in SNS (subscribers will receive confirmation emails)
4. Update your `.env` file with the appropriate topic ARNs

## Project Structure

``` txt
tech-newsletter/
├── server.js              # Main server file
├── database.js            # SQLite database operations
├── package.json           # Dependencies and scripts
├── .env.example           # Environment variables template
├── services/
│   ├── newsletter-service.js  # Newsletter sending logic
│   └── metrics-service.js     # Metrics tracking
└── utils/
    └── validation.js      # Input validation utilities
```

## Database Schema

The service uses SQLite with two main tables:

### Subscriptions

- `id` - Unique subscription ID
- `email` - Subscriber email
- `topic` - Newsletter topic
- `subscribed_at` - Subscription timestamp

### Metrics  

- `id` - Metric entry ID
- `metric_type` - Type of metric (request, subscription, etc.)
- `metric_data` - JSON data for the metric
- `timestamp` - When the metric was recorded

## Development Notes

- In development mode (when AWS SNS is not configured), the service will simulate sending emails and log the content to the console
- The SQLite database (`newsletter.db`) is created automatically on first run
- All input validation is handled with Joi schemas
- Metrics are tracked both in memory and persisted to the database

## Error Handling

The service includes comprehensive error handling:

- Input validation errors return 400 status codes
- Server errors return 500 status codes
- All errors are logged for debugging
- Graceful degradation when AWS services are unavailable

## Metrics

The service tracks:

- API request counts by endpoint
- Subscription/unsubscription counts by topic
- Newsletter sending statistics
- Error rates and types
- Service uptime

## Security Considerations

- Input sanitization for all user data
- Email validation to prevent injection
- Rate limiting should be added for production use
- AWS credentials should never be committed to version control
- Consider adding authentication for admin endpoints

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the MIT License.
