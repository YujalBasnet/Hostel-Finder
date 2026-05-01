package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.model.Hostel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/addHostel")
public class AddHostelServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        double price = Double.parseDouble(request.getParameter("price"));
        String facilities = request.getParameter("facilities");

        Hostel hostel = new Hostel();
        hostel.setName(name);
        hostel.setLocation(location);
        hostel.setPrice(price);
        hostel.setFacilities(facilities);

        HostelDAO dao = new HostelDAO();
        boolean result = dao.addHostel(hostel);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/viewHostels");
        } else {
            response.getWriter().println("Failed to add hostel");
        }
    }
}