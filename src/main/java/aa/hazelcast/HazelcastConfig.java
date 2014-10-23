package aa.hazelcast;

import com.hazelcast.config.*;

/**
 *
 * @author damien
 */
public class HazelcastConfig {

    public static Config getConfig() {
        Config cfg = new Config();
        NetworkConfig network = cfg.getNetworkConfig();
        network.setPort(5701);
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(true);
        network.getInterfaces().setEnabled(false).addInterface("10.10.1.*");

        MapConfig mapCfg = new MapConfig();
        mapCfg.setName("asks");
        mapCfg.setBackupCount(2);
        mapCfg.getMaxSizeConfig().setSize(10000);
        mapCfg.setTimeToLiveSeconds(300);
        MapStoreConfig mapStoreCfg = new MapStoreConfig();
        mapStoreCfg.setClassName("aa.hazelcast.AskMapStore").setEnabled(true);
        mapCfg.setMapStoreConfig(mapStoreCfg);
        NearCacheConfig nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setMaxSize(1000).setMaxIdleSeconds(120).setTimeToLiveSeconds(300);
        mapCfg.setNearCacheConfig(nearCacheConfig);
        cfg.addMapConfig(mapCfg);

        mapCfg = new MapConfig();
        mapCfg.setName("bids");
        mapCfg.setBackupCount(2);
        mapCfg.getMaxSizeConfig().setSize(10000);
        mapCfg.setTimeToLiveSeconds(300);
        mapStoreCfg = new MapStoreConfig();
        mapStoreCfg.setClassName("aa.hazelcast.BidMapStore").setEnabled(true);
        mapCfg.setMapStoreConfig(mapStoreCfg);
        nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setMaxSize(1000).setMaxIdleSeconds(120).setTimeToLiveSeconds(300);
        mapCfg.setNearCacheConfig(nearCacheConfig);
        cfg.addMapConfig(mapCfg);

        return cfg;
    }

}
