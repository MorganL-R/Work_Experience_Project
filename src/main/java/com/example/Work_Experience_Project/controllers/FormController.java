package com.example.Work_Experience_Project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.server.ResponseStatusException;

@Controller
public class FormController {

    static String username;
    static Integer age;

    @GetMapping(value = {"/form"})
    protected String openForm(){
        return "/submitForm";
    }

    @PostMapping(value = {"/submitForm"})
    protected String processForm(Model model,
        @RequestParam String name,
        @RequestParam String phoneNumber,
        @RequestParam LocalDate dob) throws ResponseStatusException {

        try {
            exceptionHandling(name, phoneNumber, dob);

            getAge(dob);
            customusername(dob, name);

            model.addAttribute("name", name);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("dob", dob.format(DateTimeFormatter.ofPattern("dd / MM / yyyy")));

            model.addAttribute("username", username);
            model.addAttribute("age", age);
            return "/accepted";
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void customusername(LocalDate dob, String name) {
        String namePart = name.length() >= 4 ? name.substring(0, 4) : name;
        String yearPart = String.valueOf(dob.getYear()).substring(2);

        username = namePart + yearPart;
    }

    public void getAge(LocalDate dob) {
        LocalDate today = LocalDate.now();

        if (today.getYear() == dob.getYear()) {
            age = 0;
        } else {
            age = today.compareTo(dob);
        }
    }


    public void exceptionHandling(String name, String phoneNumber, LocalDate dob) throws ResponseStatusException {
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Name can not be empty");
        }  else if (name.length() > 40) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Name contains too many characters");
        }

        if (!phoneNumber.matches("[0-9 ]*")) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Phone number must only contain digits and spaces");
        } else if (phoneNumber.length() > 15) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Phone number can not contain more than 15 characters");
        } else if (phoneNumber.length() < 9) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Phone number must contain at least 9 characters");
        }

        if (!dob.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth must be in the past");
        } else if (dob.isBefore(LocalDate.parse("1900-01-01"))) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth must be later than 01/01/1900");
        }
    }
}
