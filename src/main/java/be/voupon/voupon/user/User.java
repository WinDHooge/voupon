package be.voupon.voupon.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    @NotBlank(message= "{user.firstName}")
    @Size(max = 50, message= "{user.firstName.size}")
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message= "{user.lastName}")
    @Size(max = 50, message="{user.lastName.size}")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message="{user.emailempty}")
    @Email(message = "{user.email}")
    private String email;

    @Column(name = "password")
    @NotEmpty(message="{user.passwordempty}")
    @Size(min = 8, message= "{user.password}")
    private String passWord;

    @Transient
    private String CheckPassWord;

    @Column(name = "role")
    private String role = "USER";

    @Column(name = "active")
    private boolean active = true;


}
