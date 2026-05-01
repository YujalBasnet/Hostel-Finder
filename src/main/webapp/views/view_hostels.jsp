<%--
  Created by IntelliJ IDEA.
  User: Yujal
  Date: 4/30/2026
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*, com.hostell.hostel_finder.model.Hostel" %>

<html>
<head>
    <title>View Hostels</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>
<div class="container">
    <h2>Available Hostels</h2>

    <%
        List<Hostel> list = (List<Hostel>) request.getAttribute("hostels");

        if (list != null && !list.isEmpty()) {
            for (Hostel h : list) {
    %>
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">
        <h3><%= h.getName() %></h3>
        <p><strong>Location:</strong> <%= h.getLocation() %></p>
        <p><strong>Price:</strong> Rs. <%= h.getPrice() %></p>
        <p><strong>Facilities:</strong> <%= h.getFacilities() %></p>
    </div>
    <%
        }
    } else {
    %>
    <p>No hostels available.</p>
    <%
        }
    %>

</div>
</body>
</html>
