package be.voupon.voupon.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ContactForm {

    @NotEmpty(message="{contactform.name.empty}")
    @Size(max = 50, message="{contactform.name.size}")
    private String name;

    @NotEmpty(message="{contactform.email.empty}")
    @Email(message="{contactform.email.format}")
    private String email;

    @NotEmpty(message="{contactform.company.empty}")
    @Size(max = 50, message="{contactform.company.size}")
    private String company;

    @NotEmpty(message="{contactform.message.empty}")
    @Size(max = 255, message="{contactform.message.size}")
    private String message;

}
