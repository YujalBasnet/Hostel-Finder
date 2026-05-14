package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.ComplaintDAO;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/viewComplaints")
public class ViewComplaintsServlet extends HttpServlet {

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
        if (user.getRole() == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        ComplaintDAO dao = new ComplaintDAO();
        request.setAttribute("complaints", dao.getAllComplaints());
        request.getRequestDispatcher("/views/admin_complaints.jsp").forward(request, response);
    }

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
        if (user.getRole() == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String complaintIdParam = request.getParameter("complaintId");
        String status = request.getParameter("status");

        if (complaintIdParam == null || status == null) {
            response.sendRedirect(request.getContextPath() + "/viewComplaints");
            return;
        }

        try {
            int complaintId = Integer.parseInt(complaintIdParam);
            ComplaintDAO dao = new ComplaintDAO();
            boolean ok = dao.updateComplaintStatus(complaintId, status);
            String result = ok ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/viewComplaints?updated=" + result);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/viewComplaints?updated=error");
        }
    }
}
