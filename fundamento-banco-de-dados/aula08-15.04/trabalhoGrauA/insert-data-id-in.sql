SELECT * FROM users;
INSERT INTO users (username, user_password, user_email, user_name, user_document) VALUES (
  -- user_id: 1
  'stahlbia',
  'test123',
  'stahlbia@mail.com',
  'ana beatriz stahl',
  '04621112023'
);
INSERT INTO users (username, user_password, user_email, user_name, user_document) VALUES (
  -- user_id: 2
  'tatitata',
  'test1234',
  'tatitata@mail.com',
  'thais landfeldt',
  '12345678910'
);

-- CATEGORY INSERTS
SELECT * FROM categories;
INSERT INTO categories (category_name) VALUES (
  -- category_id: 1
  'food'
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 2
  'breakfast',
  '1' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 3
  'lunch', 
  '1' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 4
  'snacks', 
  '1' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 5
  'dinner', 
  '1' -- food category_id
);
INSERT INTO categories (category_name, user_id) VALUES (
  -- category_id: 6
  'car', 
  '1' -- stahlbia user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 7
  'gasoline', 
  '6', -- car category_id
  '1' -- stahlbia user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 8
  'parking', 
  '6', -- car category_id
  '1' -- stahlbia user_id
);
INSERT INTO categories (category_name, user_id) VALUES (
  -- category_id: 9
  'hobbies', 
  '2' -- tatitata user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 10
  'knit', 
  '9',  -- hobbies category_id
  '2' -- tatitata user_id
);

-- BANK INSERTS
SELECT * FROM bank_accounts;
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 1
  'nu bank', 
  51.92, 
  '1' -- stahlbia user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 2
  'itau', 
  129.42, 
  '1' -- stahlbia user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 3
  'neon', 
  551.27, 
  '2' -- tatitata user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 4
  'sicred', 
  632.25, 
  '2' -- tatitata user_id
);

-- GOALS INSERTS
SELECT * FROM goals;

INSERT INTO goals (goal_name, goal_expected_amount, expected_date_to_achieve_goal, goal_actual_amount, user_id) VALUES (
  -- goal_id: 1
  'new civic G10', 
  150000.0, 
  '2024-10-05', 
  100.0, 
  '1' -- stahlbia user_id
);

-- update value of the goal
UPDATE goals SET goal_actual_amount = goal_actual_amount + 10.0 WHERE goal_id = '1';

-- TRANSACTIONS INSERTS
SELECT * FROM transactions;

CREATE OR REPLACE FUNCTION update_bank_and_budget_values()
RETURNS TRIGGER AS $$
BEGIN
    -- Update the current value in the BANK_ACCOUNT table
    UPDATE bank_accounts
    SET bank_current_amount = bank_current_amount - NEW.transaction_amount
    WHERE bank_id = NEW.bank_id;
    
    -- Check if there is a corresponding budget for the transaction's category and user ID
    IF EXISTS (
        SELECT 1
        FROM referent_to rt
        JOIN budgets b ON rt.budget_id = b.budget_id
        WHERE rt.category_id = NEW.category_id
        AND b.user_id = NEW.user_id
    ) THEN
        -- Update the spend_amount in the budgets table
        UPDATE budgets
        SET spent_amount = budgets.spent_amount + NEW.transaction_amount
        FROM referent_to rt
        join budgets b on rt.budget_id = b.budget_id
        WHERE rt.category_id = NEW.category_id
        AND b.user_id = NEW.user_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_bank_and_budget_values
AFTER INSERT ON transactions
FOR EACH ROW
EXECUTE FUNCTION update_bank_and_budget_values();

INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 1
  'happy station', 
  17.00, 
  '2',  -- tatitata user_id
  '4',  -- snack category_id
  '4' -- sicredi bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 2
  'unisinos parking lot', 
  14.00, 
  '1', -- stahlbia user_id
  '8', -- parking category_id
  '2' -- itau bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 3
  'abacaxi gas station', 
  100.00, 
  '1', -- stahlbia user_id
  '7', -- gasoline category_id
  '1' -- nubank bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 4
  'groceries', 
  100.00, 
  '1', -- stahlbia user_id
  '1', -- food category_id
  '1' -- nubank bank_id
);

-- BUDGETS INSERTS
SELECT * FROM referent_to JOIN budgets on referent_to.budget_id = budgets.budget_id;
SELECT * FROM budgets;

CREATE OR REPLACE FUNCTION insert_budget_with_categories(
    new_budget_name VARCHAR(50),
    new_spent_amount FLOAT,
    new_expected_amount FLOAT,
    new_user_id UUID,
    new_category_ids UUID[]
) RETURNS VOID AS $$
DECLARE
	new_budget_id UUID;
    category_id UUID;
BEGIN
    -- Insert into budgets table
    INSERT INTO budgets (budget_name, spent_amount, expected_amount, user_id) 
    VALUES (new_budget_name, new_spent_amount, new_expected_amount, new_user_id)
    RETURNING budget_id INTO new_budget_id;
    
    -- Insert into budget_category table for each category_id
    FOREACH category_id IN ARRAY new_category_ids LOOP
        INSERT INTO referent_to (budget_id, category_id) VALUES (new_budget_id, category_id);
    END LOOP;
    
END;
$$ LANGUAGE plpgsql;

SELECT insert_budget_with_categories(
    'fixed expends'::VARCHAR(50),
    0.0::FLOAT,
    2500.0::FLOAT,
    '1'::UUID, -- stahlbia user_id
    ARRAY['6'::UUID, '1'::UUID] -- category_id
);

SELECT insert_budget_with_categories(
    'knitting line'::VARCHAR(50),
    0.0::FLOAT,
    200.0::FLOAT,
    '2'::UUID, -- tatitata user_id
    ARRAY['10'::UUID] -- category_id
);