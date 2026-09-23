package com.example.Work_Experience_Project.controllers;

import com.example.Work_Experience_Project.models.Submissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String openAdminPage(
            @RequestParam(required = false) String search,
            Model model) {

        List<Submissions> submissions =
                FormController.getSubmissions();

        if (search != null && !search.isBlank()) {

            submissions = submissions.stream()

                    .filter(submission ->

                            submission.getName()
                                    .toLowerCase()
                                    .contains(search.toLowerCase())

                                    ||

                                    submission.getEmail()
                                            .toLowerCase()
                                            .contains(search.toLowerCase())

                                    ||

                                    submission.getUsername()
                                            .toLowerCase()
                                            .contains(search.toLowerCase())

                    )

                    .toList();
        }

        model.addAttribute("submissions", submissions);

        return "adminReview";
    }
}