package Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.time.LocalDate;

//this annotation denotes it as a spring boot controller, allowing it to interact with the application
@Controller
public class FormController {

    private final HttpServletResponse httpServletResponse;

    public FormController(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    //This is an example of the part you use to map code to a html page
    @GetMapping(value = {"/submitForm"})
    protected String defaultRoute(HttpMethod httpMethod) throws Exception {
        //code here, the return value tells the program which page to go to next
        return "submitForm";
    }

    public void exceptionHandling(String name, Integer phoneNumber, LocalDate dob) throws ResponseStatusException {
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name can not be empty");
        } else if (name.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name contains too many characters");
        }

        if (phoneNumber.toString().length() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number contains too many characters");
        } else if (phoneNumber.toString().length() < 9) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number doesn't contain enough characters");
        }

        if (!dob.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth must be in the past");
        }
    }
}







