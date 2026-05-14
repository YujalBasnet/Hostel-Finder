package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.ReviewDAO;
import com.hostell.hostel_finder.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/submitReview")
public class SubmitReviewServlet extends HttpServlet {

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
        if (user.getRole() != null && "admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String hostelIdParam = request.getParameter("hostelId");
        String ratingParam = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");

        if (hostelIdParam == null || ratingParam == null || reviewText == null || reviewText.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp?review=error");
            return;
        }

        try {
            int hostelId = Integer.parseInt(hostelIdParam);
            int rating = Integer.parseInt(ratingParam);

            if (rating < 1 || rating > 5) {
                response.sendRedirect(request.getContextPath() + "/views/submit_review.jsp?hostelId=" + hostelId + "&review=error");
                return;
            }

            ReviewDAO dao = new ReviewDAO();

            // Check if user has already reviewed this hostel
            if (dao.userHasAlreadyReviewed(user.getId(), hostelId)) {
                response.sendRedirect(request.getContextPath() + "/views/submit_review.jsp?hostelId=" + hostelId + "&review=already_reviewed");
                return;
            }

            // Check if user has a booking for this hostel
            if (!dao.userHasBookingForHostel(user.getId(), hostelId)) {
                response.sendRedirect(request.getContextPath() + "/views/submit_review.jsp?hostelId=" + hostelId + "&review=no_booking");
                return;
            }

            boolean success = dao.submitReview(user.getId(), hostelId, rating, reviewText.trim());
            String result = success ? "success" : "error";
            if (success) {
                response.sendRedirect(request.getContextPath() + "/viewReviews?hostelId=" + hostelId + "&review=" + result);
            } else {
                response.sendRedirect(request.getContextPath() + "/views/submit_review.jsp?hostelId=" + hostelId + "&review=" + result);
            }
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/views/home.jsp?review=error");
        }
    }
}
