package aa.services;

import aa.hazelcast.*;
import aa.models.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import java.util.*;

/**
 *
 * @author damien
 */
public class AskService implements java.io.Serializable {
    
    private IMap<String, Ask> map;
    
    public AskService() {
        HazelcastInstance h = Hazelcast.newHazelcastInstance(HazelcastConfig.getConfig());
        map = h.getMap("asks");
    }
    
    public List<Ask> getAsks() {
        return new ArrayList(map.values());
    }
    
    public Ask getAsk(String id) {
        return map.get(id);
    }
    
    public void deleteAsk(String id) {
        map.remove(id);
    }
    
    public void addAsk(Ask ask) {
        map.put(ask.getId(), ask);
    }
    
    public void clearAsks() {
        map.clear();
    }
    
    public Ask getLowestAsk(String stock) {
        int minPrice = Integer.MAX_VALUE;
        Ask minAsk = null;
        for(Ask a:map.values()) {
            if(a.getPrice()<minPrice && a.getStock().equals(stock)) {
                minAsk = a;
                minPrice = a.getPrice();
            }
        }
        return minAsk;
    }
    
    public List<Ask> getAsksByStock(String stock) {
        return new ArrayList<>(map.values(new SqlPredicate("stock='" + stock + "'")));
    }
    
    public List<Ask> getAllUnfulfiledAsks() {
        return new ArrayList<>(map.values(new SqlPredicate("status='not matched'")));
    }
    
    public boolean hasUnfulfilledAsks(String stock) {
        return map.values(new SqlPredicate("stock='" + stock + "'" + " AND status='not matched'")).size()>0;
    }
}
