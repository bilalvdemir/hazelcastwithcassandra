package com.example.bilal.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.bilal.config.hazelcast.AbstractHazelcastConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;

@Configuration
public class UserRepositoryConfig {

    public static final String      USER_MAP = "user_map";

    @Autowired
    private AbstractHazelcastConfig hazelcastConfig;

    @PostConstruct
    public void postOperations() {

        hazelcastConfig.configureMap(USER_MAP, EvictionPolicy.LRU);
        hazelcastConfig.integrateWithStorage(USER_MAP, 0, InitialLoadMode.LAZY);

    }

}
