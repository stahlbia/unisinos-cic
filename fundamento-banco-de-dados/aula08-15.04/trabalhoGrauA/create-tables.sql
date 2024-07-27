CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
  user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  username VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL,
  user_email VARCHAR(50) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  user_document VARCHAR(11) NOT NULL
);

CREATE TABLE bank_accounts (
  bank_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  bank_name VARCHAR(50) NOT NULL,
  bank_current_amount FLOAT NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL
);

CREATE TABLE categories (
  category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  category_name VARCHAR(50),
  subcategory_id UUID REFERENCES categories(category_id),
  user_id UUID REFERENCES users(user_id)
);

CREATE TABLE transactions (
  transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  transaction_name VARCHAR(50) NOT NULL,
  transaction_amount FLOAT NOT NULL,
  transaction_occurrence_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL,
  category_id UUID REFERENCES categories(category_id) NOT NULL,
  bank_id UUID REFERENCES bank_accounts(bank_id) NOT NULL
);

CREATE TABLE budgets (
  budget_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  budget_name VARCHAR(50) NOT NULL,
  spent_amount FLOAT NOT NULL,
  expected_amount FLOAT NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL
);

CREATE TABLE referent_to (
  budget_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  category_id UUID REFERENCES categories(category_id) NOT NULL,
  budget_id UUID REFERENCES budgets(budget_id) NOT NULL
);

CREATE TABLE goals (
  goal_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  goal_name VARCHAR(50) NOT NULL,
  goal_expected_amount FLOAT NOT NULL,
  expected_date_to_achieve_goal DATE NOT NULL,
  goal_actual_amount FLOAT DEFAULT 0.00 NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL
);

DROP TABLE goals;
DROP TABLE budgets;
DROP TABLE transactions;
DROP TABLE categories;
DROP TABLE bank_accounts;
DROP TABLE users;
DROP TABLE referent_to;