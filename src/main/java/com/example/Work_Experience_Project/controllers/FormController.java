package com.example.Work_Experience_Project.controllers;

import java.time.Period;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.server.ResponseStatusException;

//Annotates this class as a controller for use in the application
@Controller
public class FormController {

    //Sets up static variables for later use as well as the logging
    private static final Log log = LogFactory.getLog(FormController.class);
    static String username;
    static Integer age;

    //Reroutes a web request to the frontend
    @GetMapping(value = {"/form"})
    protected String openForm(){
        return "/submitForm";
    }

    @PostMapping(value = {"/submitForm"})
    protected String processForm(Model model,
        //Sets the parameters for needed information in the request
        @RequestParam String name,
        @RequestParam String phoneNumber,
        @RequestParam LocalDate dob) throws ResponseStatusException {

        try {
            exceptionHandling(name, phoneNumber, dob);
            getAge(dob);
            customusername(dob, name);

            //Adds data to the model so it can be shown on frontend
            model.addAttribute("name", name);
            model.addAttribute("age", age);
            model.addAttribute("dob", dob.format(DateTimeFormatter.ofPattern("dd / MM " + "/ yyyy")));
            model.addAttribute("phoneNumber", phoneNumber);

            model.addAttribute("username", username);
            model.addAttribute("age", age);
            //Outputs response in logs
            log.info("Request successfully processed"
                + "\n Request Output:"
                + "\n   Name: " + name
                + "\n   age: " + age
                + "\n   Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd/MM" + "/yyyy"))
                + "\n   Phone Number: " + phoneNumber + "\n  Generated Username: " + username);
            return "/accepted";
            //Catches exceptions
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

      //Username creation class
      public void customusername(LocalDate dob, String name){
          String year = String.valueOf(dob.getYear());
          String cutDownName;
          String lastTwoDigits;
          //Shortens name length depending on amount of characters and adds x amount of digits from the birth year
          // depending on name length
          if(name.length() == 3){
              cutDownName = name.substring(0,name.length());
              lastTwoDigits = year.substring(year.length() - 3);
          }else if(name.length() == 2){
               cutDownName = name.substring(0, name.length());
              lastTwoDigits = year.substring(year.length() -4);
          }else{
              cutDownName = name.substring(0, 4);
              lastTwoDigits = year.substring(year.length() - 2);
          }
          username = cutDownName + lastTwoDigits;
      }

      //Calculates age by comparing current date and birthdate
    public void getAge(LocalDate dob) {
        age = Period.between(dob, LocalDate.now()).getYears();
    }

    public void exceptionHandling(String name, String phoneNumber, LocalDate dob) throws ResponseStatusException {
        //Presence check to ensure name has been entered
        if(name.isEmpty()){
            log.info("The username field is empty, please fill it and try again");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The username field is empty, please "
                + "fill it and try again");
        }else if(name.length() > 15){
            log.info("Name is too long, enter a name with less than 15 characters");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Name is too long, enter a name with "
                + "less than 15 characters");
        } else if (name.length() == 1){
            log.info("Name is too short, enter a name with more than 2 characters");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Name is too short, enter a name with"
                + " more than 2 characters");
        }
        //Ensures the phone number is not too short or too long
        if(phoneNumber.length() > 10){
            log.info("phone number too long, please enter a valid phone number. ");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "phone number too long, please enter "
                + "a valid phone number. ");
        } else if (phoneNumber.length() < 10) {
            log.info("phone number too short, please enter a valid phone number");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "phone number too short, please enter"
                + " a valid phone number");
        }
        //Ensures the date of birth is within the set parameters, preventing a date being entered that is too early
        // or too late
        if(dob.isAfter(LocalDate.now())){
            log.info("Date of birth needs to be in the past");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth needs to be in the "
                + "past");
        }else if (dob.isBefore(LocalDate.of(1900,01,01))){
            log.info("Date of birth is before the lower bound, please correct. ");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Date of birth is before the lower "
                + "bound, please correct.");
        }
    }
}