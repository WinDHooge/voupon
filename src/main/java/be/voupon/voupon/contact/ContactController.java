package be.voupon.voupon.contact;

import be.voupon.voupon.email.EmailService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {

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

    @GetMapping("/contact")
    public String showContact(Model model, Principal principal, HttpServletRequest httpServletRequest){
        if(principal != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        model.addAttribute("contactForm",new ContactForm());
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
                              Model model, Principal principal, HttpServletRequest httpServletRequest)
            throws MessagingException {

        if(principal != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
        }


        // Send simple email message (no html)
        //
        //emailService.sendContactForm(contactForm);

        // Send html email message with Voupon logo
        //
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", contactForm.getName());
        templateModel.put("text", contactForm.getMessage());
        templateModel.put("email", contactForm.getEmail());
        templateModel.put("company", contactForm.getCompany());
        templateModel.put("vouponlogo", "vouponlogo");
        emailService.sendMessageUsingThymeleafTemplate(
                contactForm.getEmail(),
                "Contactform message Voupon",
                templateModel);

        return "contact";
    }

}
