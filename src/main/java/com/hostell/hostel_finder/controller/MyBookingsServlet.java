package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.BookingDAO;
import com.hostell.hostel_finder.model.Booking;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myBookings")
public class MyBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userObj = session != null ? session.getAttribute("user") : null;
        if (!(userObj instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        User user = (User) userObj;
        if (user.getRole() != null && "admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/bookings");
            return;
        }

        BookingDAO dao = new BookingDAO();
        List<Booking> bookings = dao.getBookingsByUser(user.getId());
        request.setAttribute("bookings", bookings);

        request.getRequestDispatcher("/views/my_bookings.jsp").forward(request, response);
    }
}

