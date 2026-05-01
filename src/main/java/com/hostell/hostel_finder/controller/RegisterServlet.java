package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.UserDAO;
import com.hostell.hostel_finder.model.User;
import com.hostell.hostel_finder.util.PasswordUtil;

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
        user.setPassword(PasswordUtil.hash(password));

        UserDAO dao = new UserDAO();
        boolean result = dao.registerUser(user);

        if (result) {
            HttpSession session = request.getSession();
            session.setAttribute("success", "Registration successful! Please login.");

            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        } else {
            request.setAttribute("error", "Registration failed! Try again.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }
}