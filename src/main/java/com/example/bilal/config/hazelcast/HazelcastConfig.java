package com.example.bilal.config.hazelcast;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "!cluster" })
@Configuration
public class HazelcastConfig extends AbstractHazelcastConfig {

}
