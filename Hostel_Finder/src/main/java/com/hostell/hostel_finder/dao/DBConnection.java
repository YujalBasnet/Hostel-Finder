package com.hostell.hostel_finder.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hostel_finder";
    private static final String USER = "root"; // change if needed
    private static final String PASSWORD = "1234"; // your MySQL password

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