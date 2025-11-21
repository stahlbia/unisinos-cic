const sqlite3 = require('sqlite3').verbose();
const { v4: uuidv4 } = require('uuid');
const path = require('path');

class Database {
  constructor() {
    this.db = null;
    this.dbPath = path.join(__dirname, 'newsletter.db');
  }

  async initialize() {
    return new Promise((resolve, reject) => {
      this.db = new sqlite3.Database(this.dbPath, (err) => {
        if (err) {
          console.error('Error opening database:', err);
          reject(err);
        } else {
          console.log('Connected to SQLite database');
          this.createTables()
            .then(() => resolve())
            .catch(reject);
        }
      });
    });
  }

  async createTables() {
    const createSubscriptionsTable = `
      CREATE TABLE IF NOT EXISTS subscriptions (
        id TEXT PRIMARY KEY,
        email TEXT NOT NULL,
        topic TEXT NOT NULL,
        subscribed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        UNIQUE(email, topic)
      )
    `;

    const createMetricsTable = `
      CREATE TABLE IF NOT EXISTS metrics (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        metric_type TEXT NOT NULL,
        metric_data TEXT,
        timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
      )
    `;

    return new Promise((resolve, reject) => {
      this.db.serialize(() => {
        this.db.run(createSubscriptionsTable, (err) => {
          if (err) {
            console.error('Error creating subscriptions table:', err);
            reject(err);
            return;
          }
        });

        this.db.run(createMetricsTable, (err) => {
          if (err) {
            console.error('Error creating metrics table:', err);
            reject(err);
            return;
          }
          console.log('Database tables created successfully');
          resolve();
        });
      });
    });
  }

  async subscribeUser(email, topics) {
    const subscriptionId = uuidv4();
    
    return new Promise((resolve, reject) => {
      this.db.serialize(() => {
        const stmt = this.db.prepare(`
          INSERT OR REPLACE INTO subscriptions (id, email, topic, subscribed_at)
          VALUES (?, ?, ?, CURRENT_TIMESTAMP)
        `);

        let completed = 0;
        const total = topics.length;
        let hasError = false;

        topics.forEach((topic) => {
          stmt.run([uuidv4(), email, topic], function(err) {
            if (err && !hasError) {
              hasError = true;
              stmt.finalize();
              reject(err);
              return;
            }

            completed++;
            if (completed === total && !hasError) {
              stmt.finalize();
              resolve({ subscriptionId, email, topics });
            }
          });
        });
      });
    });
  }

  async unsubscribeUser(email, topics = null) {
    return new Promise((resolve, reject) => {
      let query, params;

      if (topics && topics.length > 0) {
        // Unsubscribe from specific topics
        const placeholders = topics.map(() => '?').join(',');
        query = `DELETE FROM subscriptions WHERE email = ? AND topic IN (${placeholders})`;
        params = [email, ...topics];
      } else {
        // Unsubscribe from all topics
        query = 'DELETE FROM subscriptions WHERE email = ?';
        params = [email];
      }

      this.db.run(query, params, function(err) {
        if (err) {
          reject(err);
        } else {
          resolve({ email, deletedCount: this.changes });
        }
      });
    });
  }

  async getUserSubscriptions(email) {
    return new Promise((resolve, reject) => {
      const query = 'SELECT topic FROM subscriptions WHERE email = ?';
      
      this.db.all(query, [email], (err, rows) => {
        if (err) {
          reject(err);
        } else {
          const topics = rows.map(row => row.topic);
          resolve(topics);
        }
      });
    });
  }

  async getSubscribersByTopic(topic) {
    return new Promise((resolve, reject) => {
      const query = 'SELECT DISTINCT email FROM subscriptions WHERE topic = ?';
      
      this.db.all(query, [topic], (err, rows) => {
        if (err) {
          reject(err);
        } else {
          const emails = rows.map(row => row.email);
          resolve(emails);
        }
      });
    });
  }

  async getSubscriberCounts() {
    return new Promise((resolve, reject) => {
      const query = `
        SELECT topic, COUNT(DISTINCT email) as subscriber_count 
        FROM subscriptions 
        GROUP BY topic
        ORDER BY subscriber_count DESC
      `;
      
      this.db.all(query, [], (err, rows) => {
        if (err) {
          reject(err);
        } else {
          const counts = {};
          rows.forEach(row => {
            counts[row.topic] = row.subscriber_count;
          });
          resolve(counts);
        }
      });
    });
  }

  async getTotalSubscribers() {
    return new Promise((resolve, reject) => {
      const query = 'SELECT COUNT(DISTINCT email) as total FROM subscriptions';
      
      this.db.get(query, [], (err, row) => {
        if (err) {
          reject(err);
        } else {
          resolve(row.total);
        }
      });
    });
  }

  async logMetric(metricType, metricData = null) {
    return new Promise((resolve, reject) => {
      const query = `
        INSERT INTO metrics (metric_type, metric_data, timestamp)
        VALUES (?, ?, CURRENT_TIMESTAMP)
      `;
      
      const dataString = metricData ? JSON.stringify(metricData) : null;
      
      this.db.run(query, [metricType, dataString], function(err) {
        if (err) {
          reject(err);
        } else {
          resolve({ id: this.lastID, metricType, metricData });
        }
      });
    });
  }

  async getMetrics(hours = 24) {
    return new Promise((resolve, reject) => {
      const query = `
        SELECT metric_type, COUNT(*) as count, MAX(timestamp) as last_occurrence
        FROM metrics 
        WHERE timestamp >= datetime('now', '-${hours} hours')
        GROUP BY metric_type
        ORDER BY count DESC
      `;
      
      this.db.all(query, [], (err, rows) => {
        if (err) {
          reject(err);
        } else {
          resolve(rows);
        }
      });
    });
  }

  async close() {
    return new Promise((resolve, reject) => {
      if (this.db) {
        this.db.close((err) => {
          if (err) {
            reject(err);
          } else {
            console.log('Database connection closed');
            resolve();
          }
        });
      } else {
        resolve();
      }
    });
  }
}

module.exports = Database;
