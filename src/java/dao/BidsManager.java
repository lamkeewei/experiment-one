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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                Timestamp date = rs.getTimestamp("date");
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
    
    public static Bid getBidById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Bid bid = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from bids where id=?";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
                       
            while(rs.next()) {
                int bidId = rs.getInt("id");
                String stock = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Timestamp date = rs.getTimestamp("date");
                String status = rs.getString("status");
                
                bid = new Bid(id, stock, price, userId, date, status);
            }            
            
            return bid; 
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
        
        return bid;
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
                Timestamp date = rs.getTimestamp("date");
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
            String sql = "insert into bids(stock, price, userid, status) values(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, bid.getStock());
            pstmt.setInt(2, bid.getPrice());
            pstmt.setString(3, bid.getUserId());
            pstmt.setString(4, bid.getStatus());
            
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
    
    public static void updateMatchBid(Bid bid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "UPDATE bids SET status=\"matched\" WHERE id=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bid.getId());
            
            pstmt.executeUpdate();
            
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
                Timestamp date = rs.getTimestamp("date");
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
    
    public static boolean hasUnfulfilledBids(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from bids where stock=? and status=\"not matched\"";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stock);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        return false;
    }
    
    public static Bid getHighestBid(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Bid bid = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "SELECT * from bids WHERE price = (SELECT MAX(price) AS max_price FROM bids WHERE stock=? AND status=\"not matched\") ORDER BY date LIMIT 1;";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, stock);
            rs = pstmt.executeQuery();
                       
            while(rs.next()) {
                int bidId = rs.getInt("id");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Timestamp date = rs.getTimestamp("date");
                String status = rs.getString("status");
                
                bid = new Bid(bidId, stock, price, userId, date, status);
            }            
            
            return bid; 
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
        
        return bid;
    } 
    
    public static void clearBids() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "DELETE FROM bids";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }
    
    public static Integer getCreditUsed(String buyerUserId){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select sum(price) * 1000 from bids where userid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, buyerUserId);
            
            rs = pstmt.executeQuery();
            
            Integer sum = 0;
            while (rs.next()) {
                sum = rs.getInt(1);
            }
            
            return sum;
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static Map<String, Integer> getAllUserBidsTotal() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<String, Integer> userBids = new HashMap<String, Integer>();
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select userid, sum(price) * 1000 as 'sum' from bids group by userid;";
            pstmt = conn.prepareCall(sql);
            
            rs = pstmt.executeQuery(sql);
            while(rs.next()) {
                String userid = rs.getString("userid");
                int totalBids = rs.getInt("sum"); 
                
                userBids.put(userid, totalBids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userBids;
    }    
}
