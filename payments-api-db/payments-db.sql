-- Drop and create a role with read and write privileges
-- Replace 'apiuser' and 'your_password' with the desired username and password
-- Grant appropriate privileges as needed
DROP ROLE IF EXISTS apiuser;
CREATE ROLE apiuser WITH LOGIN PASSWORD 'apiuseradmin12#';
GRANT CONNECT ON DATABASE postgres TO apiuser;
GRANT USAGE ON SCHEMA public TO apiuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO apiuser;

-- Connect to the default 'postgres' database
\c postgres;

-- Terminate any existing connections to the 'payments' database
SELECT pg_terminate_backend (pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'payments'
  AND pid <> pg_backend_pid();

-- Drop the 'payments' database
DROP DATABASE IF EXISTS payments;

-- Create the 'payments' database
CREATE DATABASE payments;

-- Connect to the 'payments' database
\c payments;

-- Drop the table if it exists
DROP TABLE IF EXISTS user_roles;

-- Create the User Roles table with a UUID primary key
CREATE TABLE user_roles (
    role_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

-- Example roles
INSERT INTO user_roles (role_name)
VALUES
    ('Admin'),
    ('User'),
    ('Moderator');

-- Drop the users table if it exists
DROP TABLE IF EXISTS users;

-- Create the Users table with a UUID primary key
CREATE TABLE users (
    user_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15), -- Add a column for phone numbers
    role_id UUID, -- Reference to user_roles
    FOREIGN KEY (role_id) REFERENCES user_roles (role_id)
);

-- Example users with roles
-- Insert users with specific roles
INSERT INTO users (username, email, password, phone_number, role_id)
VALUES
    ('user1', 'user1@example.com', 'password1', '',(SELECT role_id FROM user_roles WHERE role_name = 'Admin')),
    ('user2', 'user2@example.com', 'password2', '',(SELECT role_id FROM user_roles WHERE role_name = 'User')),
    ('user3', 'user3@example.com', 'password3', '',(SELECT role_id FROM user_roles WHERE role_name = 'Moderator'));



-- Drop the user_profiles table if it exists
DROP TABLE IF EXISTS user_profiles;

-- Create the User Profiles table with a UUID primary key and a BYTEA column for profile pictures
CREATE TABLE user_profiles (
    user_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    date_of_birth DATE,
    gender CHAR(1),
    country VARCHAR(50),
    bio TEXT,
    profile_picture BYTEA, -- Column for profile pictures
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);


-- Insert user profiles associated with specific users
INSERT INTO user_profiles (user_id, date_of_birth, gender, country, bio, profile_picture)
VALUES
    ((SELECT user_id FROM users WHERE username = 'user1'), '1990-01-15', 'M', 'USA', 'User 1 bio', E'\\x012345'), -- 'user1' is associated with this profile
    ((SELECT user_id FROM users WHERE username = 'user2'), '1985-04-23', 'F', 'Canada', 'User 2 bio', E'\\x987654'), -- 'user2' is associated with this profile
    ((SELECT user_id FROM users WHERE username = 'user3'), '1992-09-07', 'M', 'UK', 'User 3 bio', E'\\xABCD12'); -- 'user3' is associated with this profile


CREATE OR REPLACE FUNCTION register_user(
    p_username VARCHAR(50),
    p_email VARCHAR(100),
    p_password VARCHAR(100),
    p_phone_number VARCHAR(15),
    p_role_name VARCHAR(50),
    p_date_of_birth DATE,
    p_gender CHAR(1),
    p_country VARCHAR(50),
    p_bio TEXT,
    p_profile_picture BYTEA,
    OUT message TEXT,
    OUT user_id UUID
) AS $$
DECLARE
    v_user_id UUID;
    v_role_id UUID;
BEGIN
    -- Check if the user already exists
    IF EXISTS (SELECT 1 FROM users WHERE username = p_username) THEN
        message := 'User already exists.';
        user_id := NULL;
        RETURN;
    END IF;

    -- If role is supplied, get the role ID
    IF p_role_name IS NOT NULL THEN
        SELECT role_id INTO v_role_id FROM user_roles WHERE role_name = p_role_name;
        IF v_role_id IS NULL THEN
            message := 'Role does not exist.';
            user_id := NULL;
            RETURN;
        END IF;
    ELSE
        -- If role is not supplied, set it to 'User' role ID
        SELECT role_id INTO v_role_id FROM user_roles WHERE role_name = 'User';
    END IF;

    -- Insert a new user into the 'users' table
    INSERT INTO users (user_id, username, email, password, phone_number, role_id)
    VALUES (gen_random_uuid(), p_username, p_email, p_password, p_phone_number, v_role_id)
    RETURNING user_id INTO v_user_id;

    -- Insert user profile into the 'user_profiles' table
    INSERT INTO user_profiles (user_id, date_of_birth, gender, country, bio, profile_picture)
    VALUES (v_user_id, p_date_of_birth, p_gender, p_country, p_bio, p_profile_picture);


    message := 'User created.';
    user_id := v_user_id;
END;
$$ LANGUAGE plpgsql;
