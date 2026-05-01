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
    <title>Login - Hostel Finder</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>
<div class="container">
    <h2>Login</h2>

    <!-- SUCCESS MESSAGE -->
    <%
        String successMsg = (String) session.getAttribute("success");
        if (successMsg != null) {
    %>
    <div class="success"><%= successMsg %></div>
    <%
            session.removeAttribute("success");
        }
    %>

    <!-- ERROR MESSAGE -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error"><%= error %></div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>

    <p>Don't have an account?
        <a href="<%= request.getContextPath() %>/views/register.jsp">Register</a>
    </p>
</div>
</body>
</html></body>
