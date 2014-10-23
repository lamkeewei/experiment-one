/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aa.models;

import java.sql.Timestamp;

// represents an Ask (in a sell order)
public class Ask implements java.io.Serializable {
    static final String MATCHED = "matched";
    static final String NOT_MATCHED = "not matched";
    
    private long id;
    private String stock;
    private int price; // ask price
    private String userId; // user who made this sell order
    private Timestamp date;
    private String status;
    
    // constructor

    public Ask(long id, String stock, int price, String userId, Timestamp date, String status) {
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }
    
    public Ask(long id,String stock, int price, String userId) {
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.userId = userId;
        this.status = Ask.NOT_MATCHED;
    }
    
    public Ask(String stock, int price, String userId) {
        this.stock = stock;
        this.price = price;
        this.userId = userId;
        this.status = Ask.NOT_MATCHED;
    }

    // getters
    public String getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatusMatched() {
        status = MATCHED;
    }
    
    // toString
    public String toString() {
        return "id: "  + id + ", stock: " + stock + ", price: " + price + ", userId: " + userId + ", date: " + date + ", status: " + status; 
    }

    public void setId(Long id) {
        this.id = id;
                }
}
