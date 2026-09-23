package com.example.Work_Experience_Project.models;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class Submissions {
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private Integer age;

    public Submissions(String name,
                       String email,
                       String phoneNumber,
                       String username,
                       Integer age) {

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }
}
