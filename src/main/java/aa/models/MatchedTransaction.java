/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aa.models;

import java.sql.Timestamp;

// represents a matched bid and ask
public class MatchedTransaction {

  private Bid bid;
  private Ask ask;
  private Timestamp date;
  private int price;
  private String stock;

  // constructor
  public MatchedTransaction(Bid b, Ask a, Timestamp d, int p) {
    this.bid = b;
    this.ask = a;
    this.date = d;
    this.price = p;
    this.stock = b.getStock(); // or a.getStock(). will be the same
  }

  // getter
  public String getStock() {
    return stock;
  }

  public int getPrice() {
    return price;
  }

  public Timestamp getDate() {
    return date;
  }
  
  public long getBuyId() {
    return bid.getId();
  }
  
  public long getAskId() {
    return ask.getId();
  }

  public String getBuyerId() {
    return bid.getUserId();
  }

  public String getSellerId() {
    return ask.getUserId();
  }

  // toString
  @Override
  public String toString() {
    return "stock: " + stock + ", amt: " + price + ", bidder userId: " + bid.getUserId() + ", seller userId: " + ask.getUserId() + ", date: " + date;
  }
}