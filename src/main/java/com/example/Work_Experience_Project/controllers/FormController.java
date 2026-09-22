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

import com.example.Work_Experience_Project.services.FormService;
import org.springframework.web.server.ResponseStatusException;

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
            @RequestParam String name,
            @RequestParam String phoneNumber,
            @RequestParam String email,
            @RequestParam LocalDate dob) {

        formService.exceptionHandling(name, phoneNumber, dob, email);

        Integer age = formService.getAge(dob);

        String username = formService.customusername(dob, name);

        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("dob", dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("email", email);
        model.addAttribute("username", username);

        log.info("Request successfully processed");

        return "/accepted";
    }
}