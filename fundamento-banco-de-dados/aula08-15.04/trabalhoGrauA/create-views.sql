SELECT * FROM transaction_view;
SELECT * FROM budgets_view;
SELECT * FROM goals_view;

DROP VIEW transaction_view;
DROP VIEW budgets_view;
DROP VIEW goals_view;

CREATE OR REPLACE VIEW transaction_view AS
SELECT 
    transactions.transaction_name AS transaction_name,
    transactions.transaction_amount AS transaction_value,
    categories.category_name AS category_name,
    bank_accounts.bank_name AS bank_account_name,
    transactions.transaction_occurrence_date as ocurrence_date,
    users.user_name as username
FROM 
    transactions
JOIN 
    categories ON transactions.category_id = categories.category_id
JOIN 
    bank_accounts ON transactions.bank_id = bank_accounts.bank_id
JOIN
	users ON transactions.user_id = users.user_id;


create OR REPLACE VIEW budgets_view AS
SELECT
	budgets.budget_name as budget_name,
    budgets.spent_amount AS spent_amount,
    budgets.expected_amount as expected_amount,
    users.user_name as username
FROM
	budgets
JOIN
	users ON budgets.user_id = users.user_id;
    

create OR REPLACE VIEW goals_view AS
SELECT
	goals.goal_name as goal_name,
    goals.goal_expected_amount AS expected_amount,
    goals.goal_actual_amount as actual_value,
    goals.expected_date_to_achieve_goal as expected_date_to_achieve,
    users.user_name as username
FROM
	goals
JOIN
	users ON goals.user_id = users.user_id;