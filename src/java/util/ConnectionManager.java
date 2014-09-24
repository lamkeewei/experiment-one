package util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    
  private static String dbUser = "root";
  private static String dbPassword = "";
  private static String dbURL = "jdbc:mysql://localhost:3306/stocks_exchange";

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (Exception ex) {
      String message = "Unable to find JDBC driver for MySQL.";

      System.out.println(message);
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, message, ex);
      throw new RuntimeException(message, ex);
    }
  }

  /**
   * Gets a connection to the database
   *
   * @return the connection
   * @throws SQLException if an error occurs when connecting
   */
  public static Connection getConnection() throws SQLException {
    String message = "dbURL: " + dbURL
            + "  , dbUser: " + dbUser
            + "  , dbPassword: " + dbPassword;

    return DriverManager.getConnection(dbURL, dbUser, dbPassword);

  }

  /**
   * close the given connection, statement and resultset
   *
   * @param conn the connection object to be closed
   * @param stmt the statement object to be closed
   * @param rs the resultset object to be closed
   */
  public static void close(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, 
              "Unable to close ResultSet", ex);
    }
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, 
              "Unable to close Statement", ex);
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, 
              "Unable to close Connection", ex);
    }
  }  
}

