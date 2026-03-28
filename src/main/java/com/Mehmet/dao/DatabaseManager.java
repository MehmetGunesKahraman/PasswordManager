package com.Mehmet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:passwords.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ")";

        String createPasswordsTable = "CREATE TABLE IF NOT EXISTS passwords (" +
                "password_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "site_name TEXT NOT NULL," +
                "site_username TEXT NOT NULL," +
                "site_password TEXT NOT NULL, "  +
                "category TEXT NOT NULL" +
                "user_id INTEGER NOT NULL," +
                "CONSTRAINT fk_passwords_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)" +
                ")";

        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createPasswordsTable);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
