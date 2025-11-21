const Database = require('../database');

class MetricsService {
  constructor() {
    this.metrics = {
      requests: {},
      subscriptions: {},
      newsletters: {},
      errors: {},
      startTime: Date.now()
    };
    this.db = null;
  }

  async initialize() {
    this.db = new Database();
    await this.db.initialize();
  }

  trackRequest(method, path) {
    const key = `${method} ${path}`;
    if (!this.metrics.requests[key]) {
      this.metrics.requests[key] = 0;
    }
    this.metrics.requests[key]++;
    
    // Log to database if available
    if (this.db) {
      this.db.logMetric('request', { method, path }).catch(err => {
        console.error('Failed to log request metric:', err);
      });
    }
  }

  trackSubscription(topics) {
    topics.forEach(topic => {
      if (!this.metrics.subscriptions[topic]) {
        this.metrics.subscriptions[topic] = 0;
      }
      this.metrics.subscriptions[topic]++;
    });

    // Log to database if available
    if (this.db) {
      this.db.logMetric('subscription', { topics }).catch(err => {
        console.error('Failed to log subscription metric:', err);
      });
    }
  }

  trackUnsubscription(topics) {
    topics.forEach(topic => {
      const key = `unsubscribe_${topic}`;
      if (!this.metrics.subscriptions[key]) {
        this.metrics.subscriptions[key] = 0;
      }
      this.metrics.subscriptions[key]++;
    });

    // Log to database if available
    if (this.db) {
      this.db.logMetric('unsubscription', { topics }).catch(err => {
        console.error('Failed to log unsubscription metric:', err);
      });
    }
  }

  trackNewsletterSent(topic, subscriberCount) {
    if (!this.metrics.newsletters[topic]) {
      this.metrics.newsletters[topic] = {
        sent: 0,
        totalRecipients: 0
      };
    }
    
    this.metrics.newsletters[topic].sent++;
    this.metrics.newsletters[topic].totalRecipients += subscriberCount;

    // Log to database if available
    if (this.db) {
      this.db.logMetric('newsletter_sent', { topic, subscriberCount }).catch(err => {
        console.error('Failed to log newsletter metric:', err);
      });
    }
  }

  trackError(errorType) {
    if (!this.metrics.errors[errorType]) {
      this.metrics.errors[errorType] = 0;
    }
    this.metrics.errors[errorType]++;

    // Log to database if available
    if (this.db) {
      this.db.logMetric('error', { errorType }).catch(err => {
        console.error('Failed to log error metric:', err);
      });
    }
  }

  getMetrics() {
    const uptime = Date.now() - this.metrics.startTime;
    const uptimeHours = Math.floor(uptime / (1000 * 60 * 60));
    const uptimeMinutes = Math.floor((uptime % (1000 * 60 * 60)) / (1000 * 60));

    return {
      uptime: {
        milliseconds: uptime,
        formatted: `${uptimeHours}h ${uptimeMinutes}m`
      },
      requests: {
        total: Object.values(this.metrics.requests).reduce((sum, count) => sum + count, 0),
        byEndpoint: this.metrics.requests
      },
      subscriptions: {
        total: Object.values(this.metrics.subscriptions)
          .filter((_, key) => !Object.keys(this.metrics.subscriptions)[key].startsWith('unsubscribe_'))
          .reduce((sum, count) => sum + count, 0),
        byTopic: Object.entries(this.metrics.subscriptions)
          .filter(([key]) => !key.startsWith('unsubscribe_'))
          .reduce((acc, [topic, count]) => ({ ...acc, [topic]: count }), {}),
        unsubscriptions: Object.entries(this.metrics.subscriptions)
          .filter(([key]) => key.startsWith('unsubscribe_'))
          .reduce((acc, [key, count]) => {
            const topic = key.replace('unsubscribe_', '');
            return { ...acc, [topic]: count };
          }, {})
      },
      newsletters: {
        totalSent: Object.values(this.metrics.newsletters).reduce((sum, data) => sum + data.sent, 0),
        totalRecipients: Object.values(this.metrics.newsletters).reduce((sum, data) => sum + data.totalRecipients, 0),
        byTopic: this.metrics.newsletters
      },
      errors: {
        total: Object.values(this.metrics.errors).reduce((sum, count) => sum + count, 0),
        byType: this.metrics.errors
      },
      timestamp: new Date().toISOString()
    };
  }

  async getDatabaseMetrics(hours = 24) {
    if (!this.db) {
      return null;
    }

    try {
      const dbMetrics = await this.db.getMetrics(hours);
      const subscriberCounts = await this.db.getSubscriberCounts();
      const totalSubscribers = await this.db.getTotalSubscribers();

      return {
        period: `${hours} hours`,
        database_metrics: dbMetrics,
        current_subscribers: {
          total: totalSubscribers,
          by_topic: subscriberCounts
        }
      };
    } catch (error) {
      console.error('Error fetching database metrics:', error);
      return { error: 'Failed to fetch database metrics' };
    }
  }

  resetMetrics() {
    this.metrics = {
      requests: {},
      subscriptions: {},
      newsletters: {},
      errors: {},
      startTime: Date.now()
    };
  }

  getHealthStatus() {
    const metrics = this.getMetrics();
    const errorRate = metrics.errors.total / Math.max(metrics.requests.total, 1);
    
    let status = 'healthy';
    let issues = [];

    // Check error rate
    if (errorRate > 0.1) { // More than 10% error rate
      status = 'unhealthy';
      issues.push(`High error rate: ${(errorRate * 100).toFixed(2)}%`);
    } else if (errorRate > 0.05) { // More than 5% error rate
      status = 'degraded';
      issues.push(`Elevated error rate: ${(errorRate * 100).toFixed(2)}%`);
    }

    // Check if service has been running for a reasonable time
    if (metrics.uptime.milliseconds < 60000) { // Less than 1 minute
      issues.push('Service recently started');
    }

    return {
      status,
      issues: issues.length > 0 ? issues : undefined,
      metrics: {
        uptime: metrics.uptime.formatted,
        totalRequests: metrics.requests.total,
        totalErrors: metrics.errors.total,
        errorRate: `${(errorRate * 100).toFixed(2)}%`
      }
    };
  }
}

module.exports = MetricsService;
