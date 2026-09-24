package com.example.Work_Experience_Project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkExperienceProjectApplicationTests {

    private static final String VALID_NAME = "Kuba";
    private static final String VALID_PHONE = "1234567890";
    private static final String VALID_EMAIL = "name@example.com";
    private static final String VALID_DOB = "2007-12-21";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isOk());
    }

    @Test
    void emptyDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    void upperBoundDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", "1800-12-21"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void lowerBoundDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", "2999-12-21"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void formattingDob() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", "2007t12-21"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyEmail() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", "")
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patternEmail() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", "@test.comewan")
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sizeEmail() throws Exception {
        String longLocalPart = "a".repeat(250);

        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", longLocalPart + "@test.com")
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", "")
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tooShortPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", "123456789")
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tooLongPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", "1234567890000000")
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void alnumPhoneNum() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", VALID_NAME)
                        .param("phoneNumber", "12345t7890")
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyName() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "")
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidNameTests() throws Exception {
        unhappyPath();
    }

    @Test
    void multipleSubmissionsPerformanceTest() throws Exception {
        for (int i = 0; i < 10; i++) {
            happyPath();
        }
    }

    @Test
    void multipleMixedSubmissionsTest() throws Exception {
        for (int i = 0; i < 8; i++) {
            happyPath();
        }

        for (int i = 0; i < 2; i++) {
            unhappyPath();
        }
    }

    private void unhappyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba123!")
                        .param("phoneNumber", VALID_PHONE)
                        .param("email", VALID_EMAIL)
                        .param("dob", VALID_DOB))
                .andExpect(status().isBadRequest());
    }
}
