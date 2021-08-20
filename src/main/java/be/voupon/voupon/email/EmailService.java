package be.voupon.voupon.email;

import be.voupon.voupon.contact.ContactForm;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {

    void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel) throws MessagingException;

}
