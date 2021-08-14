package be.voupon.voupon.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

@Controller
public class UserController {

    @GetMapping("/signup")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String processForm(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        else {
            return "home";
        }
    }
}
