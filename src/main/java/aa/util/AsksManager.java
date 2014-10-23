package aa.util;

import aa.models.Ask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damien
 */
public class AsksManager implements java.io.Serializable {
    public static List<Ask> getAllAsks() {
        List<Ask> asks = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String id = rs.getString("id");
                String stock = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Timestamp date = rs.getTimestamp("date");
                String status = rs.getString("status");
                
                asks.add(new Ask(id, stock, price, userId, date, status));
            }
            
            return asks;
            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return asks;
    }
    
    public static Ask getAskById(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Ask ask = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks where id=?";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
                       
            while(rs.next()) {
                String askId = rs.getString("id");
                String stock = rs.getString("stock");
                int price = rs.getInt("price");
                String userId = rs.getString("userid");
                Timestamp date = rs.getTimestamp("date");
                String status = rs.getString("status");
                
                ask = new Ask(askId, stock, price, userId, date, status);
            }            
            
            return ask; 
            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
        
        return ask;
    }
    
    
    public static void addAsk(Ask ask){
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into asks(id, stock, price, userid, status) values(?,?,?,?,?) on duplicate key update stock=values(stock), price=values(price), userid=values(userid), status=values(status)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1,ask.getId());
            pstmt.setString(2, ask.getStock());
            pstmt.setInt(3, ask.getPrice());
            pstmt.setString(4, ask.getUserId());
            pstmt.setString(5, ask.getStatus());
            
            pstmt.execute();            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        }
    }
    
    public static void removeAsk(Ask ask) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "delete from asks where id=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ask.getId());
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        } 
    }
    
    public static void updateMatchAsk(Ask ask) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "UPDATE asks SET status=\"matched\" WHERE id=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ask.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        } 
        
    }
    
    
    
}
