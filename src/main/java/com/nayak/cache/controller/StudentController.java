package com.nayak.cache.controller;

import com.alibaba.fastjson2.JSONObject;
import com.nayak.cache.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    private StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping(value = "/addStudentData")
    public void addStudentData(@RequestBody JSONObject jsonObject) throws Exception {
        studentService.addStudentData(jsonObject);
    }


    @GetMapping(value = "/id/{id}/getStudentNameById")
    public ResponseEntity<?> getStudentNameById(@PathVariable String id) throws Exception {
        return studentService.getStudentNameById(id);
    }


    @DeleteMapping(value = "/id/{id}/deleteStudentNameById")
    public void deleteStudentNameById(@PathVariable String id) throws Exception {
        studentService.deleteStudentNameById(id);
    }


}
