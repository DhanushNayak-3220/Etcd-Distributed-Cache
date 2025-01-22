package com.nayak.cache.repository;

import com.nayak.cache.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO student (id,name,section,age,address) VALUES(?1,?2,?3,?4,?5)", nativeQuery = true)
    void addStudentData(Long l, String name, String section, int age, String address);


    @Query(value = "SELECT name FROM student WHERE id = ?1", nativeQuery = true)
    String getStudentNameById(String id);
}
