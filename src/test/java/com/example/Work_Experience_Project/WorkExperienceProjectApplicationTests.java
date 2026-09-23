package com.example.Work_Experience_Project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Work_Experience_Project.controllers.FormController;
import com.example.Work_Experience_Project.services.FormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FormController.class)
@Import(FormService.class)
class WorkExperienceProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "kuba@example.com")
                        .param("dob", "2007-12-21"))
                .andExpect(status().is(200));
    }
}