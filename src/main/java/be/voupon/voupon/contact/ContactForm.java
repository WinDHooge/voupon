package be.voupon.voupon.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ContactForm {

    @NotEmpty(message="Name cannot be empty")
    @Size(max = 50, message="Name cannot exceed 50 characters")
    private String name;

    @NotEmpty(message="Email cannot be empty")
    @Email(message="Email has invalid format")
    private String email;

    @NotEmpty(message="Company cannot be empty")
    @Size(max = 50, message="Company cannot exceed 50 characters")
    private String company;

    @NotEmpty(message="Message cannot be empty")
    @Size(max = 255, message="Message cannot exceed 255 characters")
    private String message;

}
