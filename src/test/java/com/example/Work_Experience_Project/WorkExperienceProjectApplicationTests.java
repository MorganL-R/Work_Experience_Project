package com.example.Work_Experience_Project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Work_Experience_Project.controllers.FormController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FormController.class)
class WorkExperienceProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

//    Kuba

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("dob", "2007-12-21"))
                .andExpect(status().is(200));
    }

//    Stan

    @Test
    void NullValue() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "")
                        .param("phoneNumber", "1234567890")
                        .param("dob", "2000-01-01"))
                .andExpect(status().is(500));
    }

//    Ryan

    @Test
    void dobOutOfRange() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Ryan")
                        .param("phoneNumber", "1234567890")
                        .param("dob", "9999-01-01"))
                .andExpect(status().is(500));
    }

//    Aidan

    @Test
    void phoneNumberIncorrectValue() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Ryan")
                        .param("phoneNumber", "123")
                        .param("dob", "2000-01-01"))
                .andExpect(status().is(500));
    }

//    Adam

    @Test
    void phoneNumberEmptyValue() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Ryan")
                        .param("phoneNumber", "")
                        .param("dob", "2000-01-01"))
                .andExpect(status().is(500));
    }
}