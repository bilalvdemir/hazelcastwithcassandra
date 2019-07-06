package com.example.bilal.config.hazelcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.hazelcast.HazelcastKeyValueAdapter;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;

import com.example.bilal.repository.UserRepository;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@EnableHazelcastRepositories(basePackageClasses = { UserRepository.class })
public abstract class AbstractHazelcastConfig {

    @Value("${spring.application.name}")
    protected String            applicationName;

    @Autowired
    private CassandraOperations cassandraOperations;

    @Bean
    HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", "log4j");
        config.getGroupConfig().setName(applicationName);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public KeyValueOperations keyValueTemplate() {
        return new KeyValueTemplate(new HazelcastKeyValueAdapter(hazelcastInstance()));
    }

    @Bean
    public HazelcastKeyValueAdapter hazelcastKeyValueAdapter(HazelcastInstance hazelcastInstance) {
        return new HazelcastKeyValueAdapter(hazelcastInstance);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CassandraMapStore getCassandraMapStore() {
        return new CassandraMapStore(cassandraOperations);
    }

    public MapConfig configureMap(String mapName, EvictionPolicy evictionPolicy) {
        MapConfig mapConfig = hazelcastInstance().getConfig().getMapConfig(mapName);
        if (mapConfig == null) {
            mapConfig = new MapConfig(mapName);
            mapConfig.setEvictionPolicy(evictionPolicy);
            // TODO Engineer the size of the map which will be held in memory.
            // mapConfig.setMaxSizeConfig(new MaxSizeConfig()); // Default value is Integer.MAX_VALUE. @see com.hazelcast.config.MaxSizeConfig.
            hazelcastInstance().getConfig().addMapConfig(mapConfig);
        }
        return mapConfig;
    }

    private MapStoreConfig createMapStoreConfig(int writeToDelay, InitialLoadMode loadMode) {
        MapStoreConfig mapStoreConfig = new MapStoreConfig();
        mapStoreConfig.setImplementation(getCassandraMapStore());
        mapStoreConfig.setWriteDelaySeconds(writeToDelay);
        mapStoreConfig.setInitialLoadMode(loadMode);
        return mapStoreConfig;
    }

    public void integrateWithStorage(String mapName, int writeDelay, InitialLoadMode loadMode) {
        MapConfig mapConfig = hazelcastInstance().getConfig().getMapConfig(mapName);
        MapStoreConfig mapStoreConfig = mapConfig.getMapStoreConfig();
        if (mapStoreConfig == null || !mapStoreConfig.isEnabled()) {
            mapStoreConfig = this.createMapStoreConfig(writeDelay, loadMode);
            mapConfig.setMapStoreConfig(mapStoreConfig);
        }
    }

}
