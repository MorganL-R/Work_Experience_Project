import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

//this annotation denotes it as a spring boot controller, allowing it to interact with the application
@Controller
public class FormController {

    //This is an example of the part you use to map code to a html page
    @GetMapping(value = {"/submitForm"})
    protected String processForm(Model model,
                                 @RequestParam String name,
                                 @RequestParam Integer phoneNumber,
                                 @RequestParam LocalDate dob) throws Exception {

        String Name = name.length() >= 4 ? name.substring(0, 4) : name;

        String Year = String.valueOf(dob.getYear()).substring(2);

        String username = Name + Year;

        model.addAttribute("name", name);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("dob", dob);
        model.addAttribute("username", username); // Add the new username

        return "submitForm";
    }


}

}
