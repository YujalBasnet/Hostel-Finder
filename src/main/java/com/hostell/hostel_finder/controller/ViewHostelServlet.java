package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.model.Hostel;
import com.hostell.hostel_finder.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewHostels")
public class ViewHostelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userObj = session != null ? session.getAttribute("user") : null;
        if (userObj instanceof User) {
            User user = (User) userObj;
            if (user.getRole() != null && "admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/hostels");
                return;
            }
        }

        HostelDAO dao = new HostelDAO();
        List<Hostel> list = dao.getAllHostels();

        request.setAttribute("hostels", list);

        request.getRequestDispatcher("/views/view_hostels.jsp")
                .forward(request, response);
    }
}