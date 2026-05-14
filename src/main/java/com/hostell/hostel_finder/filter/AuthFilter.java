package com.hostell.hostel_finder.filter;

import com.hostell.hostel_finder.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/views/*", "/addHostel", "/viewHostels", "/bookHostel", "/myBookings", "/myComplaints", "/logout", "/admin/*"})
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.endsWith("/login") || path.endsWith("/register")
                || path.contains("login.jsp") || path.contains("register.jsp")
                || path.contains("/views/home.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Object userObj = session != null ? session.getAttribute("user") : null;

        if (path.contains("/admin/") || path.contains("/views/admin_dashboard.jsp")) {
            if (!(userObj instanceof User) || !"admin".equalsIgnoreCase(((User) userObj).getRole())) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        if (session != null && userObj != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/views/login.jsp");
        }
    }
}
