package be.voupon.voupon.contact;

import be.voupon.voupon.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ContactController {

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String showContact(Model model){
        model.addAttribute("contactForm",new ContactForm());
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult){
        System.out.println(contactForm.getName());
        System.out.println(contactForm.getEmail());
        System.out.println(contactForm.getCompany());
        System.out.println(contactForm.getMessage());
        emailService.sendContactForm(contactForm);
        return "contact";
    }

}
