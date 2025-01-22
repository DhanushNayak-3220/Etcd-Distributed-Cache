package com.nayak.cache.cache;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
@Slf4j
@AllArgsConstructor
public class EtcdService {
    private final Client etcdClient;

    private final ObjectMapper objectMapper;
    private final KV kvClient;

    @Autowired
    public EtcdService(KV kvClient, Client etcdClient) {
        this.kvClient = kvClient;
        this.etcdClient = etcdClient;
        this.objectMapper = new ObjectMapper();
    }


    public void putKeyValue(String key, String value) throws Exception {
        KV kvClient = etcdClient.getKVClient();
        PutResponse response = kvClient.put(
                ByteSequence.from(key, StandardCharsets.UTF_8),
                ByteSequence.from(value, StandardCharsets.UTF_8)
        ).get();
        log.info("Put Response: " + response);
    }

    public String getValue(String key) throws Exception {
        log.info("Key:" + key);
        KV kvClient = etcdClient.getKVClient();
        log.info("Fetching Data From Cache");
        return kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                .get()
                .getKvs()
                .stream()
                .findFirst()
                .map(kv -> kv.getValue().toString(StandardCharsets.UTF_8))
                .orElse(null);

    }

    public void deleteValueByKey(String key) throws Exception {
        KV kv = etcdClient.getKVClient();
        ByteSequence keyByteSequence = ByteSequence.from(key, StandardCharsets.UTF_8);
        kv.delete(keyByteSequence).get();
        log.info("Successfully Deleting Cache By Key:" + key);
    }

    public void clearAllData() throws Exception {
        try {
            GetOption getOption = GetOption.newBuilder()
                    .withPrefix(ByteSequence.from(new byte[] {0}))
                    .withKeysOnly(true)
                    .build();
            GetResponse getResponse = kvClient.get(
                    ByteSequence.from(new byte[] {0}),
                    getOption
            ).get();
            if (getResponse.getKvs().isEmpty()) {
                log.info("No keys found in etcd");
                return;
            }
            int deletedCount = 0;
            for (KeyValue kv : getResponse.getKvs()) {
                DeleteResponse deleteResponse = kvClient.delete(kv.getKey()).get();
                deletedCount += deleteResponse.getDeleted();
            }
            log.info("Successfully deleted {} keys", deletedCount);
        } catch (Exception e) {
            log.error("Failed to clear data from etcd", e);
            throw e;
        }
    }

}

