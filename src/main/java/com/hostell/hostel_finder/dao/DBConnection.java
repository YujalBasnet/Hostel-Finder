package com.hostell.hostel_finder.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Reads from environment variables; falls back to localhost defaults for local dev
    private static final String URL = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:mysql://localhost:3306/hostel_finder";

    private static final String USER = System.getenv("DB_USER") != null
            ? System.getenv("DB_USER")
            : "root";

    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "1234";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
