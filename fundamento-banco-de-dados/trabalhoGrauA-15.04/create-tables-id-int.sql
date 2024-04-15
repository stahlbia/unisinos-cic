CREATE TABLE users (
  user_id SERIAL,
  username VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL,
  user_email VARCHAR(50) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  user_document VARCHAR(11) NOT NULL
);

CREATE TABLE bank_accounts (
  bank_id SERIAL,
  bank_name VARCHAR(50) NOT NULL,
  bank_current_amount FLOAT NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL
);

CREATE TABLE categories (
  category_id SERIAL,
  category_name VARCHAR(50),
  subcategory_id UUID REFERENCES categories(category_id),
  user_id UUID REFERENCES users(user_id)
);

CREATE TABLE transactions (
  transaction_id SERIAL,
  transaction_name VARCHAR(50) NOT NULL,
  transaction_amount FLOAT NOT NULL,
  transaction_occurrence_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL,
  category_id UUID REFERENCES categories(category_id) NOT NULL,
  bank_id UUID REFERENCES bank_accounts(bank_id) NOT NULL
);

CREATE TABLE budgets (
  budget_id SERIAL,
  budget_name VARCHAR(50) NOT NULL,
  spent_amount FLOAT NOT NULL,
  expected_amount FLOAT NOT NULL,
  user_id UUID REFERENCES users(user_id) NOT NULL
);

CREATE TABLE referent_to (
  budget_category_id SERIAL,
  category_id UUID REFERENCES categories(category_id) NOT NULL,
  budget_id UUID REFERENCES budgets(budget_id) NOT NULL
);

CREATE TABLE goals (
  goal_id SERIAL,
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