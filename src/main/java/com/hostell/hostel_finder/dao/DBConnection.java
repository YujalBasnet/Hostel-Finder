package com.hostell.hostel_finder.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        String url = System.getenv("DB_URL") != null
                ? System.getenv("DB_URL")
                : "jdbc:mysql://localhost:3306/hostel_finder";
        String user = System.getenv("DB_USER") != null
                ? System.getenv("DB_USER")
                : "root";
        String password = System.getenv("DB_PASSWORD") != null
                ? System.getenv("DB_PASSWORD")
                : "1234";

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}