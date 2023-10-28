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
