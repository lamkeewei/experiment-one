/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
