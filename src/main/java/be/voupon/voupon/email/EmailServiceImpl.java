package be.voupon.voupon.email;

import be.voupon.voupon.contact.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService{

    private static final String NOREPLY_ADDRESS = "noreply@javafanatics.com";

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendContactForm(ContactForm contactForm) {
        String simpleMailContent = contactForm.getName() + " sent you the following message:\n\n";
        simpleMailContent += contactForm.getMessage() + "\n\n";
        simpleMailContent += "My contact data => \n";
        simpleMailContent += "Name: " + contactForm.getName() + "\n";
        simpleMailContent += "E-mail: " + contactForm.getEmail() + "\n";
        simpleMailContent += "Company: " + contactForm.getCompany() + "\n";

        sendSimpleMessage(contactForm.getEmail(), "Message from Voupon contactform",simpleMailContent);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
