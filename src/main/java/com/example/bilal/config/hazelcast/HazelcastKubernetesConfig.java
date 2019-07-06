package com.example.bilal.config.hazelcast;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryConfig;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;

@Profile("cluster")
@Configuration
public class HazelcastKubernetesConfig extends AbstractHazelcastConfig {

    @Value("${kubernetes.namespace}")
    private String k8sNamespace;

    @Value("${kubernetes.master}")
    private String k8sMaster;

    @Bean
    HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", "log4j");
        config.getGroupConfig().setName(applicationName);

        // cluster
        config.setProperty("hazelcast.discovery.enabled", "true");
        NetworkConfig networkConfig = config.getNetworkConfig();
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        DiscoveryConfig discoveryConfig = joinConfig.getDiscoveryConfig();
        HazelcastKubernetesDiscoveryStrategyFactory factory = new HazelcastKubernetesDiscoveryStrategyFactory();
        DiscoveryStrategyConfig strategyConfig = new DiscoveryStrategyConfig(factory);
        strategyConfig.addProperty("namespace", k8sNamespace);
        strategyConfig.addProperty("service-name", applicationName);
        strategyConfig.addProperty("kubernetes-master", k8sMaster);
        discoveryConfig.addDiscoveryStrategyConfig(strategyConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

}
