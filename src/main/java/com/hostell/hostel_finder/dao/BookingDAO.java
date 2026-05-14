package com.hostell.hostel_finder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class BookingDAO {

    public boolean addBooking(int userId, int hostelId) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO bookings(user_id, hostel_id, status, created_at) VALUES (?, ?, ?, NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ps.setString(3, "pending");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<com.hostell.hostel_finder.model.Booking> getBookingsByUser(int userId) {
        List<com.hostell.hostel_finder.model.Booking> list = new java.util.ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT b.id, b.user_id, b.hostel_id, b.status, b.created_at, h.name AS hostel_name "
                    + "FROM bookings b JOIN hostels h ON b.hostel_id = h.id "
                    + "WHERE b.user_id=? ORDER BY b.created_at DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.hostell.hostel_finder.model.Booking b = new com.hostell.hostel_finder.model.Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setHostelId(rs.getInt("hostel_id"));
                b.setStatus(rs.getString("status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                b.setHostelName(rs.getString("hostel_name"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<com.hostell.hostel_finder.model.Booking> getAllBookings() {
        List<com.hostell.hostel_finder.model.Booking> list = new java.util.ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT b.id, b.user_id, b.hostel_id, b.status, b.created_at, "
                    + "h.name AS hostel_name, u.name AS user_name, u.email AS user_email "
                    + "FROM bookings b "
                    + "JOIN hostels h ON b.hostel_id = h.id "
                    + "JOIN users u ON b.user_id = u.id "
                    + "ORDER BY b.created_at DESC";
            PreparedStatement ps = conn.prepareStatement(sql);

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.hostell.hostel_finder.model.Booking b = new com.hostell.hostel_finder.model.Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setHostelId(rs.getInt("hostel_id"));
                b.setStatus(rs.getString("status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                b.setHostelName(rs.getString("hostel_name"));
                b.setUserName(rs.getString("user_name"));
                b.setUserEmail(rs.getString("user_email"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateBookingStatus(int id, String status) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE bookings SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userHasActiveBookingForHostel(int userId, int hostelId) {
        String sql = "SELECT 1 FROM bookings WHERE user_id=? AND hostel_id=? AND status IN ('pending', 'approved') LIMIT 1";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            java.sql.ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
