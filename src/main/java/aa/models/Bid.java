/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aa.models;

import java.sql.Timestamp;
import java.util.UUID;

// represents a bid (in a buy order)
public class Bid implements java.io.Serializable {

    static final String MATCHED = "matched";
    static final String NOT_MATCHED = "not matched";

    private String id = UUID.randomUUID().toString();
    private String stock;
    private int price; // bid price
    private String userId; // user who made this buy order
    private Timestamp date;
    private String status;

    public Bid(String stock, int price, String userId) {
        this.id = UUID.randomUUID().toString();
        this.stock = stock;
        this.price = price;
        this.userId = userId;
        this.status = Bid.NOT_MATCHED;
    }

    // constructor
    public Bid(String id, String stock, int price, String userId) {
        this(stock, price, userId);
        this.id = id;
    }

    public Bid(String id, String stock, int price, String userId, Timestamp date, String status) {
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.userId = userId;
        this.date = date;
        this.status = status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusMatched() {
        status = MATCHED;
    }

    // toString
    public String toString() {
        return "id: " + id + ", stock: " + stock + ", price: " + price + ", userId: " + userId + ", date: " + date + ", status: " + status;
    }
}
