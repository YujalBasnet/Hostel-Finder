package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.UserDAO;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUsersServlet extends HttpServlet {

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

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUsers();
        request.setAttribute("users", users);

        request.getRequestDispatcher("/views/admin_users.jsp").forward(request, response);
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
        if (idParam == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/users?updated=error");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/admin/users?updated=error");
            return;
        }

        UserDAO dao = new UserDAO();
        boolean ok = false;
        String actionLabel = action.toLowerCase();

        if ("suspend".equalsIgnoreCase(action)) {
            String daysParam = request.getParameter("days");
            int days;
            try {
                days = Integer.parseInt(daysParam);
            } catch (NumberFormatException ex) {
                response.sendRedirect(request.getContextPath() + "/admin/users?updated=error");
                return;
            }
            if (days <= 0) {
                response.sendRedirect(request.getContextPath() + "/admin/users?updated=error");
                return;
            }

            java.sql.Timestamp until = java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(days));
            ok = dao.updateSuspendedUntil(userId, until);
        } else if ("unsuspend".equalsIgnoreCase(action)) {
            ok = dao.updateSuspendedUntil(userId, null);
        }

        String statusLabel = ok ? "success" : "error";
        response.sendRedirect(request.getContextPath() + "/admin/users?updated=" + statusLabel + "&action=" + actionLabel);
    }
}
