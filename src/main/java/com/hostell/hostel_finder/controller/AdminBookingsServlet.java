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

@WebServlet("/admin/bookings")
public class AdminBookingsServlet extends HttpServlet {

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object userObj = session.getAttribute("user");
        if (userObj instanceof User) return (User) userObj;
        return null;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = getSessionUser(request);
        if (!isAdmin(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        BookingDAO dao = new BookingDAO();
        List<Booking> bookings = dao.getAllBookings();
        request.setAttribute("bookings", bookings);

        request.getRequestDispatcher("/views/admin_bookings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = getSessionUser(request);
        if (!isAdmin(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        String statusToSet = null;

        if ("approve".equalsIgnoreCase(action)) {
            statusToSet = "approved";
        } else if ("reject".equalsIgnoreCase(action)) {
            statusToSet = "rejected";
        }

        if (statusToSet != null && idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                BookingDAO dao = new BookingDAO();
                boolean ok = dao.updateBookingStatus(id, statusToSet);
                String statusLabel = ok ? "success" : "error";
                response.sendRedirect(request.getContextPath() + "/admin/bookings?updated=" + statusLabel + "&action=" + statusToSet);
                return;
            } catch (NumberFormatException ignored) {
                // Ignore invalid ids to avoid breaking the admin flow.
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/bookings?updated=error");
    }
}

