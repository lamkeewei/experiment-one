package aa.hazelcast;

import aa.models.Bid;
import aa.util.BidsManager;
import com.hazelcast.core.MapStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BidMapStore implements MapStore<String, Bid> {

    public void store(String key, Bid bid) {
        BidsManager.addBid(bid);
    }

    public void storeAll(Map<String, Bid> map) {
        for (String key : map.keySet()) {
            store(key, map.get(key));
        }
    }

    public void delete(String key) {
        Bid bid = load(key);
        BidsManager.removeBid(bid);
    }

    public void deleteAll(Collection<String> strings) {
        for (String key : strings) {
            delete(key);
        }
    }

    public Bid load(String key) {
        return BidsManager.getBidById(Integer.parseInt(key));
    }

    public Map<String, Bid> loadAll(Collection<String> keys) {
        Map<String, Bid> map = new HashMap<>();
        List<Bid> bids = BidsManager.getAllBids();
        for (Bid bid : bids) {
            String key = Long.toString(bid.getId());
            if(keys.contains(key)) {
                map.put(key,bid);
            }
        }
        return map;
    }

    public Set<String> loadAllKeys() {
        Set<String> keys = new HashSet<>();
        List<Bid> bids = BidsManager.getAllBids();
        for(Bid bid:bids) {
            keys.add(Long.toString(bid.getId()));
        }
        return keys;
    }
}
