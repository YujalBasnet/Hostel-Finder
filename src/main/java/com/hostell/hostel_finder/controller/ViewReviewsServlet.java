package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.dao.ReviewDAO;
import com.hostell.hostel_finder.model.Hostel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewReviews")
public class ViewReviewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String hostelIdParam = request.getParameter("hostelId");

        if (hostelIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp");
            return;
        }

        try {
            int hostelId = Integer.parseInt(hostelIdParam);
            HostelDAO hostelDAO = new HostelDAO();
            ReviewDAO reviewDAO = new ReviewDAO();

            Hostel hostel = hostelDAO.getHostelById(hostelId);
            if (hostel == null) {
                response.sendRedirect(request.getContextPath() + "/views/home.jsp");
                return;
            }

            request.setAttribute("hostelId", hostelId);
            request.setAttribute("hostelName", hostel.getName());
            request.setAttribute("reviews", reviewDAO.getReviewsByHostel(hostelId));
            request.setAttribute("averageRating", reviewDAO.getAverageRating(hostelId));

            request.getRequestDispatcher("/views/view_reviews.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp");
        }
    }
}
