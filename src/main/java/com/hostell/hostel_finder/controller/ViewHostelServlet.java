package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.model.Hostel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewHostels")
public class ViewHostelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HostelDAO dao = new HostelDAO();
        List<Hostel> list = dao.getAllHostels();

        request.setAttribute("hostels", list);

        request.getRequestDispatcher("/views/view_hostels.jsp")
                .forward(request, response);
    }
}