IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'order_db')
BEGIN
    CREATE DATABASE order_db;
END