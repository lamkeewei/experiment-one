/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import aa.Ask;
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
public class AsksManager {
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
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                asks.add(new Ask(id, stock, price, userId, date, status));
            }
            
            return asks;
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
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
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                asks.add(new Ask(id, stock, price, userId, date, status));
            }
            
            return asks;
            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "insert into asks(stock, price, userid, date, status) values(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, ask.getStock());
            pstmt.setInt(2, ask.getPrice());
            pstmt.setString(3, ask.getUserId());
            pstmt.setDate(4, new java.sql.Date(ask.getDate().getTime()));
            pstmt.setString(5, ask.getStatus());
            
            pstmt.execute();            
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
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
            pstmt.setInt(1, ask.getId());
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
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
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                
                Ask ask = new Ask(id, stockName, price, userId, date, status);
                
                asks.add(ask);
            }
            
            return asks;
        } catch (SQLException ex) {
            Logger.getLogger(BidsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        
        return null;
    }
}
