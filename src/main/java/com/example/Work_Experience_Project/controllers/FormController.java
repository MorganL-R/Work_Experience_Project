package com.example.Work_Experience_Project.controllers;

import jakarta.validation.constraints.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.Work_Experience_Project.services.FormService;

@Validated
@Controller
public class FormController {

    private static final Log log = LogFactory.getLog(FormController.class);

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("/form")
    protected String openForm() {
        return "/submitForm";
    }

    @PostMapping("/submitForm")
    protected String processForm(
            Model model,
            @RequestParam @NotBlank @Size(max = 30) @Pattern(regexp = "^[A-Za-z]+$") String name,
            @RequestParam @NotBlank @Pattern(regexp = "^\\d{10,15}$") String phoneNumber,
            @RequestParam @NotBlank @Email String email,
            @RequestParam @NotNull @Past @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dob) {


        formService.exceptionHandling(name, phoneNumber, dob, email);

        Integer age = formService.getAge(dob);

        String username = formService.customusername(dob, name);

        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("dob", dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

        log.info("Request successfully processed"
                + "\n Request Output:"
                + "\n   Name: " + name
                + "\n   age: " + age
                + "\n   email " + email
                + "\n   Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\n   Phone Number: " + phoneNumber
                + "\n  Generated Username: " + username);

        return "/accepted";
    }
}