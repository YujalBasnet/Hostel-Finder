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

            String sql = "INSERT INTO hostels(name, location, price, facilities) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, hostel.getName());
            ps.setString(2, hostel.getLocation());
            ps.setDouble(3, hostel.getPrice());
            ps.setString(4, hostel.getFacilities());

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

            String sql = "SELECT * FROM hostels";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hostel h = new Hostel();
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                h.setLocation(rs.getString("location"));
                h.setPrice(rs.getDouble("price"));
                h.setFacilities(rs.getString("facilities"));

                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}