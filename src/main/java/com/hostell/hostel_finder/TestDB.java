package com.hostell.hostel_finder;

import com.hostell.hostel_finder.dao.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        DBConnection.getConnection();
    }
}