<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register — HostelFinder</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <div class="brand">
            <a href="<%= request.getContextPath() %>/views/home.jsp" style="font-family:'Playfair Display',serif;font-size:1.8rem;font-weight:900;color:#1E1E2E;">Hostel<span style="color:#E8712A;">Finder</span></a>
            <h2 style="margin-top:16px;">Create account</h2>
            <p>Start finding your perfect hostel</p>
        </div>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %><div class="alert alert-error">❌ <%= error %></div><% } %>

        <form action="<%= request.getContextPath() %>/register" method="post">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name" placeholder="Ram Bahadur" required>
            </div>
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" placeholder="you@example.com" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Min. 8 characters" required>
            </div>
            <button type="submit" class="btn btn-primary btn-full" style="margin-top:8px;padding:13px;">Create Account</button>
        </form>

        <div class="form-footer">
            Already have an account? <a href="<%= request.getContextPath() %>/views/login.jsp">Sign in</a>
        </div>
    </div>
</div>
</body>
</html>
