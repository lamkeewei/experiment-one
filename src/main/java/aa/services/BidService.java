/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aa.services;

import aa.hazelcast.HazelcastConfig;
import aa.models.Bid;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author damien
 */
public class BidService {
    private Map<String, Bid> map;
    private IdGenerator generator;
    
    public BidService() {
        HazelcastInstance h = Hazelcast.newHazelcastInstance(HazelcastConfig.getConfig());
        map = h.getMap("bids");
        generator = h.getIdGenerator("bids");
    }
    
    public List<Bid> getBids() {
        List<Bid> result = new ArrayList<>();
        for(String key:map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }
    
    public Bid getBid(String id) {
        return map.get(id);
    }
    
    public void deleteBid(String id) {
        map.remove(id);
    }
    
    public void addBid(Bid bid) {
        bid.setId(generator.newId());
        map.put(Long.toString(bid.getId()), bid);
    }
    
    public void clearBids() {
        map.clear();
    }
    
    public Bid getHighestBid(String stock) {
        int maxPrice = Integer.MIN_VALUE;
        Bid maxBid = null;
        for(Bid b:map.values()) {
            if(b.getPrice()>maxPrice && b.getStock().equals(stock)) {
                maxBid = b;
                maxPrice = b.getPrice();
            }
        }
        return maxBid;
    }
    
    public List<Bid> getBidsByStock(String stock) {
        List<Bid> result = new ArrayList<>();
        for(Bid b:map.values()) {
            if(b.getStock().equals(stock)) {
                result.add(b);
            }
        }
        return result;
    }
    
    public List<Bid> getAllUnfulfiledBids() {
        List<Bid> result = new ArrayList<>();
        for(Bid b:map.values()) {
            if(b.getStatus().equalsIgnoreCase("not matched")) {
                result.add(b);
            }
        }
        return result;
    }
    
    public boolean hasUnfulfilledBids(String stock) {
        for(Bid b:map.values()) {
            if(b.getStock().equals(stock) && b.getStatus().equalsIgnoreCase("not matched")) {
                return true;
            }
        }
        return false;
    }
    
    public Integer getCreditUsed(String buyerUserId) {
        Map<String, Integer> result = getAllUserBidsTotal();
        int credit = result.get(buyerUserId);
        return credit;
    }
    
    public Map<String, Integer> getAllUserBidsTotal() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        int price = 0;
        for(Bid b:map.values()) {
            if(result.containsKey(b.getUserId())) {
                result.put(b.getUserId(),((int) result.get(b.getUserId()))+(b.getPrice()*1000));
            } else {
                result.put(b.getUserId(),(b.getPrice()*1000));
            }
        }
        return result;
    }
}
