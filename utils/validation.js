const Joi = require('joi');

// Available topics for validation
const VALID_TOPICS = ['ai', 'backend', 'devops', 'frontend', 'mobile'];

// Email validation schema
const emailSchema = Joi.string()
  .email({ tlds: { allow: true } })
  .required()
  .messages({
    'string.email': 'Please provide a valid email address',
    'any.required': 'Email is required'
  });

// Topic validation schema
const topicSchema = Joi.string()
  .valid(...VALID_TOPICS)
  .required()
  .messages({
    'any.only': `Topic must be one of: ${VALID_TOPICS.join(', ')}`,
    'any.required': 'Topic is required'
  });

// Newsletter content validation schema
const newsletterContentSchema = Joi.object({
  topic: topicSchema,
  subject: Joi.string()
    .min(1)
    .max(200)
    .required()
    .messages({
      'string.min': 'Subject cannot be empty',
      'string.max': 'Subject cannot exceed 200 characters',
      'any.required': 'Subject is required'
    }),
  content: Joi.string()
    .min(10)
    .max(10000)
    .required()
    .messages({
      'string.min': 'Content must be at least 10 characters long',
      'string.max': 'Content cannot exceed 10,000 characters',
      'any.required': 'Content is required'
    }),
  senderEmail: emailSchema.messages({
    'string.email': 'Please provide a valid sender email address',
    'any.required': 'Sender email is required'
  })
});

// Subscription validation schema
const subscriptionSchema = Joi.object({
  email: emailSchema,
  topics: Joi.array()
    .items(topicSchema)
    .min(1)
    .max(VALID_TOPICS.length)
    .unique()
    .required()
    .messages({
      'array.min': 'At least one topic must be selected',
      'array.max': `Cannot subscribe to more than ${VALID_TOPICS.length} topics`,
      'array.unique': 'Duplicate topics are not allowed',
      'any.required': 'Topics array is required'
    })
});

/**
 * Validate email address
 * @param {string} email - Email to validate
 * @returns {Object} Validation result
 */
function validateEmail(email) {
  return emailSchema.validate(email);
}

/**
 * Validate topic
 * @param {string} topic - Topic to validate
 * @returns {Object} Validation result
 */
function validateTopic(topic) {
  return topicSchema.validate(topic);
}

/**
 * Validate newsletter content
 * @param {Object} content - Newsletter content object
 * @returns {Object} Validation result
 */
function validateNewsletterContent(content) {
  return newsletterContentSchema.validate(content);
}

/**
 * Validate subscription data
 * @param {Object} subscription - Subscription object
 * @returns {Object} Validation result
 */
function validateSubscription(subscription) {
  return subscriptionSchema.validate(subscription);
}

/**
 * Validate array of topics
 * @param {Array} topics - Topics array to validate
 * @returns {Object} Validation result
 */
function validateTopics(topics) {
  const schema = Joi.array()
    .items(topicSchema)
    .min(1)
    .unique()
    .required();
  
  return schema.validate(topics);
}

/**
 * Sanitize email by trimming and converting to lowercase
 * @param {string} email - Email to sanitize
 * @returns {string} Sanitized email
 */
function sanitizeEmail(email) {
  if (typeof email !== 'string') {
    return email;
  }
  return email.trim().toLowerCase();
}

/**
 * Sanitize topic by trimming and converting to lowercase
 * @param {string} topic - Topic to sanitize
 * @returns {string} Sanitized topic
 */
function sanitizeTopic(topic) {
  if (typeof topic !== 'string') {
    return topic;
  }
  return topic.trim().toLowerCase();
}

/**
 * Sanitize content by trimming whitespace
 * @param {string} content - Content to sanitize
 * @returns {string} Sanitized content
 */
function sanitizeContent(content) {
  if (typeof content !== 'string') {
    return content;
  }
  return content.trim();
}

/**
 * Check if email format is valid (basic check)
 * @param {string} email - Email to check
 * @returns {boolean} True if valid format
 */
function isValidEmailFormat(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

/**
 * Check if topic is valid
 * @param {string} topic - Topic to check
 * @returns {boolean} True if valid topic
 */
function isValidTopic(topic) {
  return VALID_TOPICS.includes(topic?.toLowerCase());
}

/**
 * Get list of valid topics
 * @returns {Array} Array of valid topics
 */
function getValidTopics() {
  return [...VALID_TOPICS];
}

/**
 * Validate and sanitize subscription request
 * @param {Object} data - Raw subscription data
 * @returns {Object} Validation result with sanitized data
 */
function validateAndSanitizeSubscription(data) {
  // Sanitize input data
  const sanitized = {
    email: sanitizeEmail(data.email),
    topics: Array.isArray(data.topics) 
      ? data.topics.map(topic => sanitizeTopic(topic))
      : data.topics
  };

  // Validate sanitized data
  const validation = validateSubscription(sanitized);
  
  if (validation.error) {
    return validation;
  }

  return {
    error: null,
    value: sanitized
  };
}

/**
 * Validate and sanitize newsletter content
 * @param {Object} data - Raw newsletter data
 * @returns {Object} Validation result with sanitized data
 */
function validateAndSanitizeNewsletter(data) {
  // Sanitize input data
  const sanitized = {
    topic: sanitizeTopic(data.topic),
    subject: sanitizeContent(data.subject),
    content: sanitizeContent(data.content),
    senderEmail: sanitizeEmail(data.senderEmail)
  };

  // Validate sanitized data
  const validation = validateNewsletterContent(sanitized);
  
  if (validation.error) {
    return validation;
  }

  return {
    error: null,
    value: sanitized
  };
}

module.exports = {
  validateEmail,
  validateTopic,
  validateTopics,
  validateNewsletterContent,
  validateSubscription,
  validateAndSanitizeSubscription,
  validateAndSanitizeNewsletter,
  sanitizeEmail,
  sanitizeTopic,
  sanitizeContent,
  isValidEmailFormat,
  isValidTopic,
  getValidTopics,
  VALID_TOPICS
};
