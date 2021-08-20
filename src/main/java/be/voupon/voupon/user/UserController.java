package be.voupon.voupon.user;

import be.voupon.voupon.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println(e);
        }
    }

    @GetMapping("/signup")
    public String add(Model model, Principal principal, HttpServletRequest httpServletRequest) {
        User user = principal != null ? userService.getUserByEmail(principal.getName()) : new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String processForm(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request)
            throws MessagingException {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            userService.save(user);
            authWithHttpServletRequest(request, user.getEmail(), user.getCheckPassWord());

            // Send welcome email
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("firstname", user.getFirstName());
            templateModel.put("lastname", user.getLastName());
            templateModel.put("email", user.getEmail());
            templateModel.put("vouponlogo", "vouponlogo");
            templateModel.put("emailtemplate", "email/signup.html");

            emailService.sendMessageUsingThymeleafTemplate(
                    user.getEmail(),
                    "Welcome to Voupon",
                    templateModel);

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

        return "redirect:/account/dashboard";
    }

    @GetMapping("/login")
    public String init(Model model) {
        model.addAttribute("msg", "Please Enter Your Login Details");
        return "login";
    }

    @GetMapping("/account/dashboard")
    public String showDashboard(Model model, Principal principal, HttpServletRequest httpServletRequest) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "account/dashboard";
    }
}
