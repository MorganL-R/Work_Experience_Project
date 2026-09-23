package com.example.Work_Experience_Project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Work_Experience_Project.controllers.FormController;
import com.example.Work_Experience_Project.services.FormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FormController.class)
class WorkExperienceProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FormService formService;

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "name@example.com")
                        .param("dob", "2007-12-21"))
                .andExpect(status().is(200));
    }
}
