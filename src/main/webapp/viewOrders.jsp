<%@page import="aa.*" %>
<jsp:useBean id="exchangeService" scope="application" class="aa.services.ExchangeService" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Unfulfilled Orders</title>
    </head>
    <BODY>
        <!-- unfulfilled bids of SMU -->
        <table border="1">
            <thead>
                <tr>
                    <th>For SMU</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Unfulfilled Bids (Buy Orders)</td>
                    <td><%=exchangeService.getUnfulfilledBidsForDisplay("smu")%></td>
                </tr>
                <tr>
                    <td>Unfulfilled Asks (Sell Orders)</td>
                    <td><%=exchangeService.getUnfulfilledAsks("smu")%></td>
                </tr>

            </tbody>
        </table>

        <br/><br/>

        <!-- unfulfilled bids of NUS -->
        <table border="1">
            <thead>
                <tr>
                    <th>For NUS</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Unfulfilled Bids (Buy Orders)</td>
                    <td><%=exchangeService.getUnfulfilledBidsForDisplay("nus")%></td>
                </tr>
                <tr>
                    <td>Unfulfilled Asks (Sell Orders)</td>
                    <td><%=exchangeService.getUnfulfilledAsks("nus")%></td>
                </tr>

            </tbody>
        </table>

        <br/><br/>

        <!-- unfulfilled bids of NTU -->
        <table border="1">
            <thead>
                <tr>
                    <th>For NTU</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Unfulfilled Bids (Buy Orders)</td>
                    <td><%=exchangeService.getUnfulfilledBidsForDisplay("ntu")%></td>
                </tr>
                <tr>
                    <td>Unfulfilled Asks (Sell Orders)</td>
                    <td><%=exchangeService.getUnfulfilledAsks("ntu")%></td>
                </tr>

            </tbody>
        </table>

        <br/><br/>

        <!-- show remaining credit of each buyer -->
        Remaining Credit of Each User
        <table border="1">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Remaining credit</th>
                </tr>
            </thead>
            <tbody>
                <%=exchangeService.getAllCreditRemainingForDisplay()%>
            </tbody>
        </table>
        <hr/>
        Functions available:<br/>
        <ul>
            <li><a href="buy.jsp">Buy</a></li>
            <li><a href="sell.jsp">Sell</a></li>
            <li><a href="logout.jsp">Log out</a></li>
            <li><a href="current.jsp">Current stats</a></li>
            <li><a href="viewOrders.jsp">Unfulfilled orders</a></li>
        </ul>
    </BODY>
</html>