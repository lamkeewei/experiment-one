<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aa.*" %>
<jsp:useBean id="exchangeService" scope="application" class="aa.services.ExchangeService" />

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>End Trading Day</title>
  </head>
  <body>
    <h1>Your Trading Day has ended!</h1>

    <%
      exchangeService.endTradingDay(); // clean up instance variables
      session.invalidate();
    %>

  </body>
</html>
