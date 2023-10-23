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
-- INSERT INTO users (username, email, password, role_id)
-- VALUES
--     ('user1', 'user1@example.com', 'password1', 'UUID_of_Role1'),
--     ('user2', 'user2@example.com', 'password2', 'UUID_of_Role2');


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

-- Example user profiles with profile pictures
-- INSERT INTO user_profiles (user_id, username, password, first_name, last_name, email, date_of_birth, gender, country, bio, profile_picture)
-- VALUES
--     ('UUID_of_User1', 'John', 'password1', 'John', 'Doe', 'user1@example.com', '1990-01-01', 'M', 'USA', 'Bio for user1', BYTEA_DATA1),
--     ('UUID_of_User2', 'Jane', 'password2', 'Jane', 'Smith', 'user2@example.com', '1985-03-15', 'F', 'Canada', 'Bio for user2', BYTEA_DATA2);
