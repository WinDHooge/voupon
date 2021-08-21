package be.voupon.voupon.contact;

import be.voupon.voupon.email.EmailService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

    // Example on how to add additional images if needed
    /*
        @Value("classpath:/static/images/company_logos/1.png")
        private Resource testImageFile1;

        @Value("classpath:/static/images/company_logos/3.png")
        private Resource testImageFile2;

        @Value("classpath:/static/images/company_logos/4.png")
        private Resource testImageFile3;

     */

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

        if (bindingResult.hasErrors()) {
            return "contact";
        }


        // Send a html email message with Voupon logo & optional additional inline images
        //

        // Create the templateModel info
        // (emailtemplate is mandatory)
        //
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", contactForm.getName());
        templateModel.put("text", contactForm.getMessage());
        templateModel.put("email", contactForm.getEmail());
        templateModel.put("company", contactForm.getCompany());
        templateModel.put("vouponlogo", "vouponlogo");
        templateModel.put("emailtemplate", "email/contact.html");

        // Example on how to add additional images if needed
        /*
            Map<String, Resource> images = new HashMap<>();
            images.put("image1",testImageFile1);
            images.put("image2",testImageFile2);
            images.put("image3",testImageFile3);

            templateModel.put("images", images);
            for (Map.Entry<String, Resource> entry : images.entrySet()) {
                templateModel.put(entry.getKey(),entry.getKey());
            }
        */

        emailService.sendMessageUsingThymeleafTemplate(
                contactForm.getEmail(),
                "Contactform message Voupon",
                templateModel);

        model.addAttribute("success", "Thank you for your message. We'll get back to you as soon as possible.");

        return "contact";
    }

}
