package be.voupon.voupon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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

        try {
            userService.save(user);
        } catch (UserService.PasswordException e) {
            bindingResult.rejectValue("passWord","user.password",e.getMessage());
            return "signup";
        } catch (UserService.PasswordMisMatchException e) {
            bindingResult.rejectValue("passWord","password-mismatch",e.getMessage());
            return "signup";
        } catch (UserService.UniqueUserException e) {
            bindingResult.rejectValue("email","user.unique",e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String init(Model model) {
        model.addAttribute("msg", "Please Enter Your Login Details");
        return "login";
    }

    @GetMapping("/account/dashboard")
    public String showDashboard(Model model) {
        return "account/dashboard";
    }
}
