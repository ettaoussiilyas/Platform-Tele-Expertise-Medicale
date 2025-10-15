-- Create database if not exists
CREATE DATABASE IF NOT EXISTS medical_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE medical_platform;

-- Grant privileges (adjust username/password as needed)
GRANT ALL PRIVILEGES ON medical_platform.* TO 'root'@'localhost';
FLUSH PRIVILEGES;