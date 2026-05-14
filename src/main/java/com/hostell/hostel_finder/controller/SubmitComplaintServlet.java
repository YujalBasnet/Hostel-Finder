package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.ComplaintDAO;
import com.hostell.hostel_finder.dao.BookingDAO;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/submitComplaint")
public class SubmitComplaintServlet extends HttpServlet {

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

        String hostelIdParam = request.getParameter("hostelId");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        if (hostelIdParam == null || subject == null || message == null || 
            subject.trim().isEmpty() || message.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/myComplaints?submitted=error");
            return;
        }

        try {
            int hostelId = Integer.parseInt(hostelIdParam);
            BookingDAO bookingDAO = new BookingDAO();
            if (!bookingDAO.userHasActiveBookingForHostel(user.getId(), hostelId)) {
                response.sendRedirect(request.getContextPath() + "/myComplaints?submitted=no_booking");
                return;
            }

            ComplaintDAO dao = new ComplaintDAO();
            boolean success = dao.submitComplaint(user.getId(), hostelId, subject.trim(), message.trim());
            String result = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/myComplaints?submitted=" + result);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/myComplaints?submitted=error");
        }
    }
}
