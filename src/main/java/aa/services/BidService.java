package aa.services;

import aa.hazelcast.HazelcastConfig;
import aa.models.Bid;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author damien
 */
public class BidService {
    private IMap<String, Bid> map;
    
    public BidService() {
        HazelcastInstance h = Hazelcast.newHazelcastInstance(HazelcastConfig.getConfig());
        map = h.getMap("bids");
    }
    
    public List<Bid> getBids() {
        return new ArrayList(map.values());
    }
    
    public Bid getBid(String id) {
        return map.get(id);
    }
    
    public void deleteBid(String id) {
        map.remove(id);
    }
    
    public void addBid(Bid bid) {
        map.put(bid.getId(), bid);
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
       return new ArrayList<>(map.values(new SqlPredicate("stock='" + stock + "'")));
    }
    
    public List<Bid> getAllUnfulfiledBids() {
        return new ArrayList<>(map.values(new SqlPredicate("status='not matched'")));
    }
    
    public boolean hasUnfulfilledBids(String stock) {
        return map.values(new SqlPredicate("stock='" + stock + "'" + " AND status='not matched'")).size()>0;
    }
    
    public Integer getCreditUsed(String buyerUserId) {
        Map<String, Integer> result = getAllUserBidsTotal();
        if(result==null) {
            return 0;
        }
        Integer credit = result.get(buyerUserId);
        if (credit==null) {
            return 0;
        } else {
            return credit;
        }
    }
    
    public Map<String, Integer> getAllUserBidsTotal() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        if(map==null) {
            return result;
        }
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
