package com.nayak.cache.config;

import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtcdConfig {

    @Value("${etcd.endpoints}")
    private String endpoints;

    @Bean
    public Client etcdClient() {
        return Client.builder()
                .endpoints(endpoints)
                .build();
    }

    @Bean
    public KV kv(Client client){
        return etcdClient().getKVClient();
    }
}
