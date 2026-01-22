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

    /*@Test
    void happyPath() throws Exception {
        mockMvc.perform(post("/submitForm")
                        .param("name", "Kuba")
                        .param("phoneNumber", "1234567890")
                        .param("dob", "2007-12-21"))
                .andExpect(status().is(200));
    }*/

  @Test
  void happyPathReturns200Response() throws Exception {
    mockMvc.perform(post("/submitForm")
        .param("name", "Joshua")
        .param("phoneNumber", "1234567890")
        .param("dob", "2008-11-13")).andExpect(status().is(200));

  }
// Test for if name is 1 character
  @Test
  void nameTooShortTriggerTest() throws Exception {
    mockMvc.perform(post("/submitForm")
        .param("name", "J")
        .param("phoneNumber", "1234567890")
        .param("dob", "2008-11-13")).andExpect(status().is(500));
  }
// Test for name longer than 15 characters
  @Test
  void nameTooLongTriggerTest() throws Exception {
    mockMvc.perform(post("/submitForm")
        .param("name", "JJJJJJJJJJJJJJJJJJJ")
        .param("phoneNumber", "1234567890")
        .param("dob", "2008-11-13")).andExpect(status().is(500));
  }
// Test for if phone number is too long
  @Test
  void phoneNumberTooLongTriggerTest() throws Exception {
    mockMvc.perform(post("/submitForm")
        .param("name", "Josh")
        .param("phoneNumber", "1565545454545465454545454465455454545455645454654545456455456454545456565")
        .param("dob", "2008-11-13")).andExpect(status().is(500));
  }
// Test for if phone number is too short
  @Test
  void phoneNumberTooShortTriggerTest() throws Exception {
    mockMvc.perform(post("/submitForm")
        .param("name", "Josh")
        .param("phoneNumber", "1")
        .param("dob", "2008-11-13")).andExpect(status().is(500));
  }

  @Test
  void dobUpperBoundExceptionTest() throws Exception{
    mockMvc.perform(post("/submitForm")
        .param("name", "Josh")
        .param("phoneNumber", "1234567890")
        .param("dob", "2077-11-13")).andExpect(status().is(500));
  }

  @Test
  void dobLowerBoundExceptionTest() throws Exception{
    mockMvc.perform(post("/submitForm")
        .param("name", "Josh")
        .param("phoneNumber", "1234567890")
        .param("dob", "1425-11-13")).andExpect(status().is(500));

  }
}