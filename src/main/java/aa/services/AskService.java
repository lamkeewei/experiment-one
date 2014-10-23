package aa.services;

import aa.hazelcast.*;
import aa.models.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.*;
import com.hazelcast.core.IdGenerator;

/**
 *
 * @author damien
 */
public class AskService implements java.io.Serializable {
    
    private Map<String, Ask> map;
    private IdGenerator generator;
    
    public AskService() {
        HazelcastInstance h = Hazelcast.newHazelcastInstance(HazelcastConfig.getConfig());
        map = h.getMap("asks");
        generator = h.getIdGenerator("asks");
        addAsk(new Ask("SMU", 12, "lamkeewei"));
        addAsk(new Ask("NTU", 11, "damienng"));
        addAsk(new Ask("SIM", 10, "lamkeewei"));
        addAsk(new Ask("SMU", 12, "lamkeewei"));
    }
    
    public List<Ask> getAsks() {
        List<Ask> result = new ArrayList<>();
        for(String key:map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }
    
    public Ask getAsk(String id) {
        return map.get(id);
    }
    
    public void deleteAsk(String id) {
        map.remove(id);
    }
    
    public void addAsk(Ask ask) {
        ask.setId(generator.newId());
        map.put(Long.toString(ask.getId()), ask);
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
        List<Ask> result = new ArrayList<>();
        for(Ask b:map.values()) {
            if(b.getStock().equals(stock)) {
                result.add(b);
            }
        }
        return result;
    }
    
    public List<Ask> getAllUnfulfiledAsks() {
        List<Ask> result = new ArrayList<>();
        for(Ask b:map.values()) {
            if(b.getStatus().equalsIgnoreCase("not matched")) {
                result.add(b);
            }
        }
        return result;
    }
    
    public boolean hasUnfulfilledAsks(String stock) {
        for(Ask b:map.values()) {
            if(b.getStock().equals(stock) && b.getStatus().equalsIgnoreCase("not matched")) {
                return true;
            }
        }
        return false;
    }
}
