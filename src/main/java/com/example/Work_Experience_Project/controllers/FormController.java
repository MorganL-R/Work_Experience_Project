package com.example.Work_Experience_Project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
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
        @RequestParam Integer phoneNumber,
        @RequestParam LocalDate dob) throws Exception {

        model.addAttribute("name", name);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("dob", dob);

        getAge(dob);
        customUsername(dob, name);
        model.addAttribute("username", username);
        model.addAttribute("age", age);
        return "/accepted";
    }

    public String customUsername(LocalDate dob, String name) {

        String namePart = name.length() >= 4 ? name.substring(0, 4) : name;

        String yearPart = String.valueOf(dob.getYear()).substring(2);

        username = namePart + yearPart;

        return username;
    }

    public Integer getAge(LocalDate dob) {
        LocalDate today = LocalDate.now();

        return today.compareTo(dob);
    }


    public void exceptionHandling(String name, String phoneNumber, LocalDate dob) throws ResponseStatusException {
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name can not be empty");
        } else if (name.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name contains too many characters");
        }

        if (!phoneNumber.matches("[0-9]*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must only contain digits");
        } else if (phoneNumber.length() > 16) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Phone number can not contain more than 10 characters");
        } else if (phoneNumber.length() < 9) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Phone number must contain ta least 9 characters");
        }

        if (!dob.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth must be in the past");
        }
    }
}
