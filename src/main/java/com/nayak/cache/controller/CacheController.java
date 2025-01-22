package com.nayak.cache.controller;

import com.nayak.cache.cache.EtcdService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final EtcdService etcdService;

    @Autowired
    private CacheController(EtcdService etcdService) {
        this.etcdService = etcdService;
    }

    @DeleteMapping(value = "/deleteAllEtcdCache")
    public void deleteAllEtcdCache() throws Exception {
        etcdService.clearAllData();
    }

    @DeleteMapping(value = "/cacheKey/{key}/deleteAllEtcdCache")
    public void deleteAllEtcdCache(@PathVariable String key) throws Exception {
        etcdService.deleteValueByKey(key);
    }

}
