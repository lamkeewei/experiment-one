package aa.hazelcast;

import aa.models.Ask;
import aa.util.AsksManager;
import com.hazelcast.core.MapStore;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AskMapStore implements MapStore<String, Ask> {

    public void store(String key, Ask ask) {
        AsksManager.addAsk(ask);
    }

    public void storeAll(Map<String, Ask> map) {
        for (String key : map.keySet()) {
            store(key, map.get(key));
        }
    }

    public void delete(String key) {
        Ask ask = load(key);
        AsksManager.removeAsk(ask);
    }

    public void deleteAll(Collection<String> strings) {
        for (String key : strings) {
            delete(key);
        }
    }

    public Ask load(String key) {
        return AsksManager.getAskById(key);
    }

    public Map<String, Ask> loadAll(Collection<String> keys) {
        Map<String, Ask> map = new ConcurrentHashMap<>();
        List<Ask> asks = AsksManager.getAllAsks();
        for (Ask ask : asks) {
            String key = ask.getId();
            if (keys.contains(key)) {
                map.put(key, ask);
            }
        }
        return map;
    }

    public Set<String> loadAllKeys() {
        Set<String> keys = new HashSet<>();
        List<Ask> asks = AsksManager.getAllAsks();
        if (asks == null || asks.isEmpty()) {
            return keys;
        } else {
            for (Ask ask : asks) {
                keys.add(ask.getId());
            }
            return keys;
        }
    }
}
