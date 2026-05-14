package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.BookingDAO;
import com.hostell.hostel_finder.dao.ComplaintDAO;
import com.hostell.hostel_finder.model.Booking;
import com.hostell.hostel_finder.model.Complaint;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myComplaints")
public class MyComplaintsServlet extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/viewComplaints");
            return;
        }

        ComplaintDAO complaintDAO = new ComplaintDAO();
        BookingDAO bookingDAO = new BookingDAO();

        List<Complaint> complaints = complaintDAO.getComplaintsByUser(user.getId());
        List<Booking> bookedHostels = bookingDAO.getBookingsByUser(user.getId());

        request.setAttribute("complaints", complaints);
        request.setAttribute("bookedHostels", bookedHostels);
        request.getRequestDispatcher("/views/my_complaints.jsp").forward(request, response);
    }
}
