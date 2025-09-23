import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
public class FormController {

    static String username;
    static Integer age;

    @GetMapping(value = {"/submitForm"})
    protected String processForm(Model model,
                                 @RequestParam String name,
                                 @RequestParam Integer phoneNumber,
                                 @RequestParam LocalDate dob) throws Exception {

        model.addAttribute("name", name);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("dob", dob);

        Getage(dob);
        customusername(dob, name);
        model.addAttribute("username", username);
        model.addAttribute("age", age);



        return "submitForm";
    }

    public String customusername(LocalDate dob, String name) {

        String namePart = name.length() >= 4 ? name.substring(0, 4) : name;

        String yearPart = String.valueOf(dob.getYear()).substring(2);

        String username = namePart + yearPart;

        return username;
    }

    public Integer Getage(LocalDate dob) {
        LocalDate today = LocalDate.now();

        return today.compareTo(dob);
    }
}


