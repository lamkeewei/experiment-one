/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aa.util;

import aa.models.Ask;
import aa.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamkeewei
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
                int id = rs.getInt("id");
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
        
        return null;
    }
    
    public static Ask getAskById(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Ask ask = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks where id=?";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
                       
            while(rs.next()) {
                int askId = rs.getInt("id");
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
    
    public static List<Ask> getAllUnfulfiledAsk(){
        List<Ask> asks = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks where status='not matched'";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("id");
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
        
        return null;
    }
    
    public static void addAsk(Ask ask){
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "insert into asks(id, stock, price, userid, status) values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setLong(1, ask.getId());
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
            pstmt.setLong(1, ask.getId());
            
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
            pstmt.setLong(1, ask.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, null);
        } 
        
    }
    
    public static List<Ask> getAsksByStock(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Ask> asks = new ArrayList<>();
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks where stock=?";
            
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
                
                Ask ask = new Ask(id, stockName, price, userId, date, status);
                
                asks.add(ask);
            }
            
            return asks;
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
    public static boolean hasUnfulfilledAsks(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select * from asks where stock=? and status=\"not matched\"";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stock);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        return false;
    }
    
    public static Ask getLowestAsk(String stock) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Ask ask = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "SELECT * from asks WHERE price = (SELECT MIN(price) AS min_price FROM asks WHERE stock=? AND status=\"not matched\") AND status=\"not matched\" ORDER BY date LIMIT 1;";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, stock);
            rs = pstmt.executeQuery();
                       
            while(rs.next()) {
                int askId = rs.getInt("id");
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
    
    public static void clearAsks() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "DELETE FROM asks";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AsksManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
    }
}
