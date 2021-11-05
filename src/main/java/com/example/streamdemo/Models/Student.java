package com.example.streamdemo.Models;

import com.fasterxml.jackson.annotation.JsonTypeId;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Student {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private long counter;

    public Student() {
    }

    public Student(String name, long counter) {
        this.counter = counter;
        this.name = name;
    }
}
