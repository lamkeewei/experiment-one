
<%@page import="aa.*" %>
<%@page import="aa.util.MatchedTransactionManager" %>
<jsp:useBean id="exchangeService" scope="application" class="aa.services.ExchangeService" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Current Stats</title>
    </head>


     <BODY>
            <table border="1">
                <thead>
                    <tr>
                        <th>Stats for SMU</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Latest Price</td>
                        <td><%=MatchedTransactionManager.getLatestPrice("smu")%></td>
                    </tr>
                    <tr>
                        <td>Current Highest Bid</td>
                        <td><%=exchangeService.getHighestBidPrice("smu")%></td>
                    </tr>
                    <tr>
                        <td>Current Lowest Ask</td>
                        <td><%=exchangeService.getLowestAskPrice("smu")%></td>
                    </tr>
                </tbody>
            </table>

            <br/><br/>

            <table border="1">
                <thead>
                    <tr>
                        <th>Stats for NUS</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Latest Price</td>
                        <td><%=MatchedTransactionManager.getLatestPrice("nus")%></td>
                    </tr>
                    <tr>
                        <td>Current Highest Bid</td>
                        <td><%=exchangeService.getHighestBidPrice("nus")%></td>
                    </tr>
                    <tr>
                        <td>Current Lowest Ask</td>
                        <td><%=exchangeService.getLowestAskPrice("nus")%></td>
                    </tr>
                </tbody>
            </table>

            <br/><br/>

            <table border="1">
                <thead>
                    <tr>
                        <th>Stats for NTU</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Latest Price</td>
                        <td><%=MatchedTransactionManager.getLatestPrice("ntu")%></td>
                    </tr>
                    <tr>
                        <td>Current Highest Bid</td>
                        <td><%=exchangeService.getHighestBidPrice("ntu")%></td>
                    </tr>
                    <tr>
                        <td>Current Lowest Ask</td>
                        <td><%=exchangeService.getLowestAskPrice("ntu")%></td>
                    </tr>
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