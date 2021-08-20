package be.voupon.voupon.email;

import be.voupon.voupon.contact.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService{

    private static final String NOREPLY_ADDRESS = "noreply@javafanatics.com";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("classpath:/static/images/email/email-logo.png")
    private Resource emailLogoFile;


    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process((String) templateModel.get("emailtemplate"), thymeleafContext);

        Map<String, Resource> images = (Map<String, Resource>) templateModel.get("images");

        sendHtmlMessage(to, subject, htmlBody, images);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody, Map<String, Resource> images) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("vouponlogo", emailLogoFile);

        if(images != null){
            for (Map.Entry<String, Resource> entry : images.entrySet()) {
                helper.addInline(entry.getKey(),entry.getValue());
            }
        }

        emailSender.send(message);
    }

}
