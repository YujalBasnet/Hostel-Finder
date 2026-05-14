package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.BookingDAO;
import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.model.Hostel;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/bookHostel")
public class BookHostelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userObj = session != null ? session.getAttribute("user") : null;
        if (!(userObj instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        User user = (User) userObj;
        if (user.getRole() != null && "admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp?booked=error");
            return;
        }

        try {
            int hostelId = Integer.parseInt(idParam);
            HostelDAO hostelDAO = new HostelDAO();
            Hostel hostel = hostelDAO.getHostelById(hostelId);
            if (hostel == null || hostel.getStatus() == null || !"approved".equalsIgnoreCase(hostel.getStatus())) {
                response.sendRedirect(request.getContextPath() + "/views/home.jsp?booked=unavailable");
                return;
            }

            BookingDAO dao = new BookingDAO();
            if (dao.userHasActiveBookingForHostel(user.getId(), hostelId)) {
                response.sendRedirect(request.getContextPath() + "/myBookings?booked=duplicate");
                return;
            }

            boolean ok = dao.addBooking(user.getId(), hostelId);
            String result = ok ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/myBookings?booked=" + result);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp?booked=error");
        }
    }
}

