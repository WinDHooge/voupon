package be.voupon.voupon.email;

import be.voupon.voupon.contact.ContactForm;

public interface EmailService {

    void sendContactForm(ContactForm contactForm);

}
