<%--
  Created by IntelliJ IDEA.
  User: Yujal
  Date: 4/30/2026
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <title>Register</title>
</head>
<body>


<div class="container">
    <h2>Register</h2>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error"><%= error %></div>
    <%
        }
    %>
    <form action="<%= request.getContextPath() %>/register" method="post">
        <input type="text" name="name" placeholder="Full Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Register</button>
    </form>

    <p>Already have an account?
        <a href="login.jsp">Login</a>
    </p>
</div>
</body>
</html>
