package com.hostell.hostel_finder.dao;

import com.hostell.hostel_finder.model.Hostel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO {

    public boolean addHostel(Hostel hostel) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO hostels(name, location, price, facilities, status, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            String status = hostel.getStatus();
            if (status == null || status.trim().isEmpty()) {
                status = "pending";
            }

            ps.setString(1, hostel.getName());
            ps.setString(2, hostel.getLocation());
            ps.setDouble(3, hostel.getPrice());
            ps.setString(4, hostel.getFacilities());
            ps.setString(5, status);
            ps.setString(6, hostel.getImagePath());

            int rows = ps.executeUpdate();

            if (rows > 0) success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public List<Hostel> getAllHostels() {
        List<Hostel> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM hostels WHERE status='approved'";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hostel h = new Hostel();
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                h.setLocation(rs.getString("location"));
                h.setPrice(rs.getDouble("price"));
                h.setFacilities(rs.getString("facilities"));
                h.setStatus(rs.getString("status"));
                h.setImagePath(rs.getString("image"));

                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Hostel> getPendingHostels() {
        List<Hostel> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM hostels WHERE status='pending'";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hostel h = new Hostel();
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                h.setLocation(rs.getString("location"));
                h.setPrice(rs.getDouble("price"));
                h.setFacilities(rs.getString("facilities"));
                h.setStatus(rs.getString("status"));
                h.setImagePath(rs.getString("image"));

                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateHostelStatus(int id, String status) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE hostels SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
    public boolean rejectHostel(int id) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE hostels SET status='rejected' WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Hostel getHostelById(int id) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM hostels WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Hostel h = new Hostel();
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                h.setLocation(rs.getString("location"));
                h.setPrice(rs.getDouble("price"));
                h.setFacilities(rs.getString("facilities"));
                h.setStatus(rs.getString("status"));
                h.setImagePath(rs.getString("image"));
                return h;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}