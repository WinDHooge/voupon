package be.voupon.voupon.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class User {

    @NotBlank(message= "{user.firstName}")
    @Size(max = 50, message="First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message= "{user.lastName}")
    @Size(max = 50, message="Last name cannot exceed 50 characters")
    private String lastName;


    @NotBlank(message= "{user.email}")
    private String email;

    private String passWord;

    @Transient
    private String CheckPassWord;


}
