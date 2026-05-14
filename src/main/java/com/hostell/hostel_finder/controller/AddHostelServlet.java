package com.hostell.hostel_finder.controller;

import com.hostell.hostel_finder.dao.HostelDAO;
import com.hostell.hostel_finder.model.Hostel;
import com.hostell.hostel_finder.util.ValidationUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/addHostel")
@MultipartConfig
public class AddHostelServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userObj = session != null ? session.getAttribute("user") : null;
        if (!(userObj instanceof com.hostell.hostel_finder.model.User)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        com.hostell.hostel_finder.model.User user = (com.hostell.hostel_finder.model.User) userObj;
        if (user.getRole() != null && "admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String priceParam = request.getParameter("price");
        String facilities = request.getParameter("facilities");

        double price;
        try {
            price = Double.parseDouble(priceParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher("/views/add_hostel.jsp").forward(request, response);
            return;
        }

        if (ValidationUtil.isBlank(name) || ValidationUtil.isBlank(location)
                || ValidationUtil.isBlank(facilities) || !ValidationUtil.isPositive(price)) {
            request.setAttribute("error", "All fields are required and price must be greater than 0.");
            request.getRequestDispatcher("/views/add_hostel.jsp").forward(request, response);
            return;
        }

        Part imagePart = request.getPart("image");
        String imagePath = "";
        if (imagePart != null && imagePart.getSize() > 0) {
            String submittedName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String safeName = submittedName.replaceAll("[^A-Za-z0-9._-]", "_");
            String fileName = System.currentTimeMillis() + "_" + safeName;
            String uploadRoot = getServletContext().getRealPath("/uploads");
            if (uploadRoot != null) {
                Path uploadDir = Paths.get(uploadRoot);
                Files.createDirectories(uploadDir);
                Path target = uploadDir.resolve(fileName);
                imagePart.write(target.toString());
                imagePath = "uploads/" + fileName;
            }
        }

        Hostel hostel = new Hostel();
        hostel.setName(name);
        hostel.setLocation(location);
        hostel.setPrice(price);
        hostel.setFacilities(facilities);
        hostel.setStatus("pending");
        hostel.setImagePath(imagePath);

        HostelDAO dao = new HostelDAO();
        boolean result = dao.addHostel(hostel);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/views/add_hostel.jsp?submitted=pending");
        } else {
            request.setAttribute("error", "Failed to submit hostel. Please try again.");
            request.getRequestDispatcher("/views/add_hostel.jsp").forward(request, response);
        }
    }
}