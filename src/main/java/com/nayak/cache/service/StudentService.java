package com.nayak.cache.service;


import com.alibaba.fastjson2.JSONObject;
import com.nayak.cache.cache.EtcdService;
import com.nayak.cache.repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

    private final StudentRepo studentRepo;

    private final EtcdService etcdService;

    @Autowired
    private StudentService(StudentRepo studentRepo, EtcdService etcdService) {
        this.studentRepo = studentRepo;
        this.etcdService = etcdService;
    }

    public void addStudentData(JSONObject jsonObject) throws Exception {
//        etcdService.clearAllData(); // When ever you are Performing ADD,DELETE,UPDATE Operation don't forget to clear your cache
        studentRepo.addStudentData(System.currentTimeMillis(), jsonObject.getString("name"), jsonObject.getString("section"),
                jsonObject.getInteger("age"), jsonObject.getString("address"));
        log.info("SuccessFully Added Student Info For Student:" + jsonObject.getString("name"));
    }


    public ResponseEntity<?> getStudentNameById(String id) {
        try {
            String cacheKey = String.format("getStudentNameById-%s", id);

            String cachedValue = etcdService.getValue(cacheKey);
            if (cachedValue != null && !cachedValue.isEmpty()) {
                log.info("Cache hit for key: {}", cacheKey);
                return ResponseEntity.ok(cachedValue);
            }
            log.info("Cache miss for key: {}", cacheKey);
            String name = studentRepo.getStudentNameById(id);
            if (cachedValue == null) {
                etcdService.putKeyValue(cacheKey, name);
                log.info("Cached value for key: {}", cacheKey);
            }
            return ResponseEntity.ok(name);
        } catch (Exception e) {
            log.error("Error while fetching data for id: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing request: " + e.getMessage());
        }
    }


    public void deleteStudentNameById(String id) throws Exception {
        etcdService.clearAllData();// When ever you are Performing ADD,DELETE,UPDATE Operation don't forget to clear your cache
        studentRepo.deleteById(id);
    }
}
