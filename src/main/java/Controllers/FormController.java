package Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    public void exceptionHandling(String ) throws Exception {

    }
}







