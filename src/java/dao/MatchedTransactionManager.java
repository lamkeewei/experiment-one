/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import aa.Ask;
import aa.Bid;
import aa.MatchedTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ConnectionManager;

/**
 *
 * @author lamkeewei
 */
public class MatchedTransactionManager {
    public static void addMatchedTransaction(MatchedTransaction match) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into matched_transaction(bid_id, ask_id, date, price, stock) values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, match.getBuyId());
            pstmt.setInt(2, match.getAskId());
            pstmt.setTimestamp(3, new java.sql.Timestamp(match.getDate().getTime()));
            pstmt.setInt(4, match.getPrice());
            pstmt.setString(5, match.getStock());
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MatchedTransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
    }
    
    public static List<MatchedTransaction> getMatchedTransactionByStock(String stock){
        List<MatchedTransaction> matched = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from matched_transaction M, bids B, asks A " +
                            "where M.bid_id = B.id " +
                            "and M.ask_id = A.id " + 
                            "and M.stock=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stock);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int bidId = rs.getInt("B.id");
                String bidStock = rs.getString("B.stock");
                int bidPrice = rs.getInt("B.price");
                String bidUserId = rs.getString("B.userid");
                Date bidDate = rs.getDate("B.date");
                String bidStatus = rs.getString("B.status");
                
                Bid bid = new Bid(bidId, stock, bidPrice, bidUserId, bidDate, bidStatus);
                
                int askId = rs.getInt("A.id");
                String askStock = rs.getString("A.stock");
                int askPrice = rs.getInt("A.price");
                String askUserId = rs.getString("A.userid");
                Date askDate = rs.getTimestamp("A.date");
                String askStatus = rs.getString("A.status");
                
                Ask ask = new Ask(askId, askStock, askPrice, askUserId, askDate, askStatus);
                
                Date matchedDate = rs.getDate("M.date");
                int matchedPrice = rs.getInt("M.price");
                
                MatchedTransaction mtxn = new MatchedTransaction(bid, ask, matchedDate, matchedPrice);
                matched.add(mtxn);
            }
            
            return matched;
        } catch (SQLException ex) {
            Logger.getLogger(MatchedTransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
