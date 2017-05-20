<%--
  Created by IntelliJ IDEA.
  User: vitiv
  Date: 5/13/17
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%
    String user = (String) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect("welcome.jsp");
        return;
    }
%>

<form method="post" action="/login">
    <label for="user">Nume de utilizator:</label><input id="user" type="text" name="user"/>
    <label for="password">Parola:</label><input id="password" type="password" name="password"/>
    <input type="submit" value="Log in"/>
</form>

</body>
</html>
