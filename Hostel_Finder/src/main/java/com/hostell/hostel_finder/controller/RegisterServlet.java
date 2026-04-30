package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.UserDAO;
import com.hostell.hostel_finder.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Servlet hit!");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserDAO dao = new UserDAO();
        boolean result = dao.registerUser(user);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        } else {
            response.getWriter().println("Registration Failed!");
        }
    }
}