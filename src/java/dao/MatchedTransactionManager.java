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
    public void addMatchedTrasnaction(Bid bid, Ask ask, Date date, int price) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into matched_transaction(bid_id, ask_id, date, price, stock) values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, bid.getId());
            pstmt.setInt(2, ask.getId());
            pstmt.setDate(3, new java.sql.Date(date.getTime()));
            pstmt.setInt(4, price);
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MatchedTransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
    }
    
    public static List<MatchedTransaction> getMatchedTransactionByStock(String stock){
        List<MatchedTransaction> matched = new ArrayList<>();
        
        return matched;
    }
}
