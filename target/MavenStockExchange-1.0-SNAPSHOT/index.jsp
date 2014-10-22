<%-- 
    Document   : index
    Created on : Oct 22, 2014, 5:39:16 PM
    Author     : damien
--%>
<%@page import="aa.*" %>
<%@page import="java.util.*" %>
<%@page import="aa.services.*" %>
<%@page import="aa.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="askService" scope="application" class="aa.services.AskService" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Asks:</h2>
        <%=askService.getAsks().size()%>
    </body>
</html>
