package aa.util;

import aa.models.Bid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damien
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
        
        return bids;
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
   
    public static void addBid(Bid bid){
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into bids(id, stock, price, userid, status) values(?,?,?,?,?) on duplicate key update stock=values(stock), price=values(price), userid=values(userid), status=values(status), ";
           
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setLong(1, bid.getId());
            pstmt.setString(2, bid.getStock());
            pstmt.setInt(3, bid.getPrice());
            pstmt.setString(4, bid.getUserId());
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
            pstmt.setLong(1, bid.getId());
            
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
            pstmt.setLong(1, bid.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        } 
        
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
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return userBids;
    }    
}
