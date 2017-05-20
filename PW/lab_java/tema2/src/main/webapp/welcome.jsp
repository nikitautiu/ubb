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
    <title>Welcome</title>
</head>
<body>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

Sunteti logat ca <%=(String) session.getAttribute("user") %><br>
Va putei deloga <a href="logout.jsp">delogati</a>.

</body>
</html>
