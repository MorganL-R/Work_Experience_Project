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

    @Test
    void emptyDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "name@example.com")
                        .param("dob", ""))
                .andExpect(status().is(400));
    }

    @Test
    void upperBoundDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "name@example.com")
                        .param("dob", "1800-12-21"))
                .andExpect(status().is(400));
    }

    @Test
    void lowerBoundDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "name@example.com")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(400));
    }

    @Test
    void formattingDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "name@example.com")
                        .param("dob", "2026t12-21"))
                .andExpect(status().is(400));
    }
    @Test
    void emptyEmail() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(400));
    }

    @Test
    void patternEmail() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "@test.comewan")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(400));
    }

    @Test
    void sizeEmail() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("email", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(500));
    }

    @Test
    void emptyPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "")
                        .param("email", "name@example.com")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(400));
    }

    @Test
    void sizePhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "123456789000000")
                        .param("email", "name@example.com")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(500));
    }

    @Test
    void alnumPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "12345t7890")
                        .param("email", "name@example.com")
                        .param("dob", "2026-12-21"))
                .andExpect(status().is(400));
    }
}
