package aa;

import java.sql.Timestamp;
import java.util.Date;

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
  
  public int getBuyId() {
    return bid.getId();
  }
  
  public int getAskId() {
    return ask.getId();
  }

  public String getBuyerId() {
    return bid.getUserId();
  }

  public String getSellerId() {
    return ask.getUserId();
  }

  // toString
  public String toString() {
    return "stock: " + stock + ", amt: " + price + ", bidder userId: " + bid.getUserId() + ", seller userId: " + ask.getUserId() + ", date: " + date;
  }
}
