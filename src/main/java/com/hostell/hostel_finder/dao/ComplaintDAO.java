package com.hostell.hostel_finder.dao;

import com.hostell.hostel_finder.model.Complaint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    public boolean submitComplaint(int userId, int hostelId, String subject, String message) {
        String sql = "INSERT INTO complaints(user_id, hostel_id, subject, message, status, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ps.setString(3, subject);
            ps.setString(4, message);
            ps.setString(5, "pending");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.hostel_id, c.subject, c.message, c.status, c.created_at, " +
                "u.name AS user_name, h.name AS hostel_name " +
                "FROM complaints c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN hostels h ON c.hostel_id = h.id " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setId(rs.getInt("id"));
                complaint.setUserId(rs.getInt("user_id"));
                complaint.setHostelId(rs.getInt("hostel_id"));
                complaint.setSubject(rs.getString("subject"));
                complaint.setMessage(rs.getString("message"));
                complaint.setStatus(rs.getString("status"));
                complaint.setCreatedAt(rs.getTimestamp("created_at"));
                complaint.setUserName(rs.getString("user_name"));
                complaint.setHostelName(rs.getString("hostel_name"));
                list.add(complaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Complaint> getComplaintsByUser(int userId) {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.hostel_id, c.subject, c.message, c.status, c.created_at, " +
                "h.name AS hostel_name " +
                "FROM complaints c " +
                "JOIN hostels h ON c.hostel_id = h.id " +
                "WHERE c.user_id = ? " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Complaint complaint = new Complaint();
                    complaint.setId(rs.getInt("id"));
                    complaint.setUserId(rs.getInt("user_id"));
                    complaint.setHostelId(rs.getInt("hostel_id"));
                    complaint.setSubject(rs.getString("subject"));
                    complaint.setMessage(rs.getString("message"));
                    complaint.setStatus(rs.getString("status"));
                    complaint.setCreatedAt(rs.getTimestamp("created_at"));
                    complaint.setHostelName(rs.getString("hostel_name"));
                    list.add(complaint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateComplaintStatus(int complaintId, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, complaintId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
