SELECT * FROM users;
INSERT INTO users (username, user_password, user_email, user_name, user_document) VALUES (
  -- user_id: 2b21b8b4-c44d-457f-8cf6-9da0f242607c
  'stahlbia',
  'test123',
  'stahlbia@mail.com',
  'ana beatriz stahl',
  '04621112023'
);
INSERT INTO users (username, user_password, user_email, user_name, user_document) VALUES (
  -- user_id: 996e99d7-cb3c-4a4e-a699-d3e977cf4218
  'tatitata',
  'test1234',
  'tatitata@mail.com',
  'thais landfeldt',
  '12345678910'
);

-- CATEGORY INSERTS
SELECT * FROM categories;
INSERT INTO categories (category_name) VALUES (
  -- category_id: 3a34537c-9bd2-42d1-bfdd-445e63a936a3
  'food'
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 96fb3186-ce3e-48ea-8019-c8706d9660b7
  'breakfast',
  '3a34537c-9bd2-42d1-bfdd-445e63a936a3' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 3de3554a-2157-4ed2-a26b-c757cc06d6da
  'lunch', 
  '3a34537c-9bd2-42d1-bfdd-445e63a936a3' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 5f863d4f-a18f-4577-b3b0-2e7f29b978b3
  'snacks', 
  '3a34537c-9bd2-42d1-bfdd-445e63a936a3' -- food category_id
);
INSERT INTO categories (category_name, subcategory_id) VALUES (
  -- category_id: 7568a7f7-ebd8-450a-a7c6-9206ebe48704
  'dinner', 
  '3a34537c-9bd2-42d1-bfdd-445e63a936a3' -- food category_id
);
INSERT INTO categories (category_name, user_id) VALUES (
  -- category_id: 0c234397-3630-4c39-b56b-85cad0b1d6f0
  'car', 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 2d94daf8-690a-4e27-b264-848e607ff4b7
  'gasoline', 
  '0c234397-3630-4c39-b56b-85cad0b1d6f0', -- car category_id
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 5d7107c2-8058-4ae9-b4be-ea62c72efcfd
  'parking', 
  '0c234397-3630-4c39-b56b-85cad0b1d6f0', -- car category_id
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);
INSERT INTO categories (category_name, user_id) VALUES (
  -- category_id: 0bf2438e-6425-44d0-87ed-d182bb1a459b
  'hobbies', 
  '996e99d7-cb3c-4a4e-a699-d3e977cf4218' -- tatitata user_id
);
INSERT INTO categories (category_name, subcategory_id, user_id) VALUES (
  -- category_id: 62f3502f-c7c8-40de-82cb-b9393636bea0
  'knit', 
  '0bf2438e-6425-44d0-87ed-d182bb1a459b',  -- hobbies category_id
  '996e99d7-cb3c-4a4e-a699-d3e977cf4218' -- tatitata user_id
);

-- BANK INSERTS
SELECT * FROM bank_accounts;
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 9a2cdeff-779a-48ae-bd18-626f98922f55
  'nu bank', 
  51.92, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: f1f8ec69-1fa3-4957-b8bf-0de8d752fc3e
  'itau', 
  129.42, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: 4561a364-261b-401b-86cf-07223236d4d1
  'neon', 
  551.27, 
  '996e99d7-cb3c-4a4e-a699-d3e977cf4218' -- tatitata user_id
);
INSERT INTO bank_accounts (bank_name, bank_current_amount, user_id) VALUES (
  -- bank_id: b74b54c9-0bea-4345-8567-7b9451e41576
  'sicred', 
  632.25, 
  '996e99d7-cb3c-4a4e-a699-d3e977cf4218' -- tatitata user_id
);

-- GOALS INSERTS
SELECT * FROM goals;

INSERT INTO goals (goal_name, goal_expected_amount, expected_date_to_achieve_goal, goal_actual_amount, user_id) VALUES (
  -- goal_id: 7c751ede-41c8-4b7c-b23f-2b4a37b20db6
  'new civic G10', 
  150000.0, 
  '2024-10-05', 
  100.0, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c' -- stahlbia user_id
);

-- update value of the goal
UPDATE goals SET goal_actual_amount = goal_actual_amount + 10.0 WHERE goal_id = '7c751ede-41c8-4b7c-b23f-2b4a37b20db6';

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
  -- transaction_id: 
  'happy station', 
  17.00, 
  '996e99d7-cb3c-4a4e-a699-d3e977cf4218',  -- tatitata user_id
  '5f863d4f-a18f-4577-b3b0-2e7f29b978b3',  -- snack category_id
  'b74b54c9-0bea-4345-8567-7b9451e41576' -- sicredi bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 
  'unisinos parking lot', 
  14.00, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c', -- stahlbia user_id
  '5d7107c2-8058-4ae9-b4be-ea62c72efcfd', -- parking category_id
  'f1f8ec69-1fa3-4957-b8bf-0de8d752fc3e' -- itau bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 
  'abacaxi gas station', 
  100.00, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c', -- stahlbia user_id
  '2d94daf8-690a-4e27-b264-848e607ff4b7', -- gasoline category_id
  '9a2cdeff-779a-48ae-bd18-626f98922f55' -- nubank bank_id
);
INSERT INTO TRANSACTIONS (transaction_name, transaction_amount, user_id, category_id, bank_id) VALUES (
  -- transaction_id: 
  'groceries', 
  100.00, 
  '2b21b8b4-c44d-457f-8cf6-9da0f242607c', -- stahlbia user_id
  '3a34537c-9bd2-42d1-bfdd-445e63a936a3', -- food category_id
  '9a2cdeff-779a-48ae-bd18-626f98922f55' -- nubank bank_id
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
    '2b21b8b4-c44d-457f-8cf6-9da0f242607c'::UUID, -- stahlbia user_id
    ARRAY['0c234397-3630-4c39-b56b-85cad0b1d6f0'::UUID, '3a34537c-9bd2-42d1-bfdd-445e63a936a3'::UUID] -- category_id
);

SELECT insert_budget_with_categories(
    'knitting line'::VARCHAR(50),
    0.0::FLOAT,
    200.0::FLOAT,
    '996e99d7-cb3c-4a4e-a699-d3e977cf4218'::UUID, -- tatitata user_id
    ARRAY['62f3502f-c7c8-40de-82cb-b9393636bea0'::UUID] -- category_id
);