<%--
  Created by IntelliJ IDEA.
  User: Yujal
  Date: 4/30/2026
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Hostel</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>
<div class="container">
    <h2>Add Hostel</h2>

    <form action="<%= request.getContextPath() %>/addHostel" method="post">
        <input type="text" name="name" placeholder="Hostel Name" required>
        <input type="text" name="location" placeholder="Location" required>
        <input type="number" name="price" placeholder="Price" required>
        <input type="text" name="facilities" placeholder="Facilities (WiFi, Food, Parking)">
        <button type="submit">Add Hostel</button>
    </form>
</div>
</body>
</html>
