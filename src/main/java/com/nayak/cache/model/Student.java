package com.nayak.cache.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {

    @Id
    private String id;

    private String name;

    private String section;

    private String address;

    private int age;
}
