package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.UserDAO;
import com.hostell.hostel_finder.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.loginUser(email, password);

        if (user != null) {
            if (user.getSuspendedUntil() != null && user.getSuspendedUntil().after(new java.util.Date())) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                request.setAttribute("error", "Account suspended until " + fmt.format(user.getSuspendedUntil()) + ".");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String role = user.getRole();
            if (role != null && role.equalsIgnoreCase("admin")) {
                response.sendRedirect(request.getContextPath() + "/admin/hostels");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/home.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid email or password!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}