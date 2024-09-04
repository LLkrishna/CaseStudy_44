package com.cts.coms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/CUSTOMER_DATABASE";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@DataBase23@";

    // Private constructor to prevent instantiation
    private DataBaseConnection() {}

    // Method to get the single instance of the database connection
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DataBaseConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                        System.out.println("Database connected successfully.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Set to null after closing
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
