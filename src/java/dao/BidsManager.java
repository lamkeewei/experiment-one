/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import aa.Bid;
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
public class BidsManager {
    public static List<Bid> getAllBids() {
        List<Bid> bids = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from bids";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String stock = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                bids.add(new Bid(id, stock, price, userId, date, status));
            }
            
            return bids;
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
    public static List<Bid> getAllUnfulfiledBids(){
        List<Bid> bids = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from bids where status='not matched'";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String stock = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                bids.add(new Bid(id, stock, price, userId, date, status));
            }
            
            return bids;
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
    public static void addBid(Bid bid){
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into bids(stock, price, userid, date, status) values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, bid.getStock());
            pstmt.setInt(2, bid.getPrice());
            pstmt.setString(3, bid.getUserId());
            pstmt.setDate(4, new java.sql.Date(bid.getDate().getTime()));
            pstmt.setString(5, bid.getStatus());
            
            pstmt.execute();            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
    }
    
    public static void removeBid(Bid bid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "delete from bids where id=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bid.getId());
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        } 
    }
    
    public static List<Bid> getBidsByStock(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Bid> bids = new ArrayList<>();
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from bids where stock=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stock);
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String stockName = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                Bid bid = new Bid(id, stock, price, userId, date, status);
                
                bids.add(bid);
            }
            
            return bids;
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
    }
}
