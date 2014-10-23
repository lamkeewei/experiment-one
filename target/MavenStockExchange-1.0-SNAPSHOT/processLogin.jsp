<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Process Login</title>
    </head>
    <body>
        <%
          session.setAttribute("userId",request.getParameter("id").trim());
          session.setAttribute("authenticatedUser",true);
        %> 
        <jsp:forward page = "loginSuccess.jsp" />
    </body>
</html>
