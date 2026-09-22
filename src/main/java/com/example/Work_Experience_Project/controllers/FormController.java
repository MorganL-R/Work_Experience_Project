package com.example.Work_Experience_Project.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
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

    private static final Log log = LogFactory.getLog(FormController.class);
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
        @RequestParam String email,
        @RequestParam LocalDate dob) throws ResponseStatusException {

        try {
            exceptionHandling(name, phoneNumber, dob, email);


            getAge(dob);
            customusername(dob, name);


            model.addAttribute("name", name);
            model.addAttribute("age", age);
            model.addAttribute("dob", dob.format(DateTimeFormatter.ofPattern("dd / MM " + "/ yyyy")));
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("email", email);

            model.addAttribute("username", username);
            model.addAttribute("age", age);
            log.info("Request successfully processed"
                + "\n Request Output:"
                + "\n   Name: " + name
                + "\n   age: " + age
                + "\n   email " + email
                + "\n   Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd/MM" + "/yyyy"))
                + "\n   Phone Number: " + phoneNumber + "\n  Generated Username: " + username);
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
        age = today.getYear() - dob.getYear();

        if (today.getMonthValue() < dob.getMonthValue()) {
            age = age - 1;
        } else if (today.getMonthValue() == dob.getMonthValue()) {
            if (today.getDayOfMonth() < dob.getDayOfMonth()) {
                age = age - 1;
            }
        }
    }


    public void exceptionHandling(String name, String phoneNumber, LocalDate dob, String email) throws ResponseStatusException {
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

        if (email.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Email can not be empty");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Email format is invalid");
        }



        if (!dob.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth must be in the past");
        } else if (dob.isBefore(LocalDate.parse("1900-01-01"))) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth must be later than 01/01/1900");
        }
    }
}
