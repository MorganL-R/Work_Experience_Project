package com.example.Work_Experience_Project.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@Service
public class FormService {

    public String customusername(LocalDate dob, String name) {

        String namePart = name.length() >= 4 ? name.substring(0, 4) : name;
        String yearPart = String.valueOf(dob.getYear()).substring(2);

        return namePart + yearPart;
    }

    public Integer getAge(LocalDate dob) {

        LocalDate today = LocalDate.now();

        int age = Period.between(dob, today).getYears();

        return age;
    }

    public void exceptionHandling(
            String name,
            String phoneNumber,
            LocalDate dob,
            String email) {



        if (!dob.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Date of birth must be in the past");
        }

    }
}