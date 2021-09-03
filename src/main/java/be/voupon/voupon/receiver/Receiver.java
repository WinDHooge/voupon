package be.voupon.voupon.receiver;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name= "receivers")
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    @NotBlank(message = "{receiver.firstName}")
    @Size(max = 50, message= "{receiver.firstName.size}")
    private String firstName;

    @NotBlank(message= "{receiver.lastName}")
    @Size(max = 50, message="{receiver.lastName.size}")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "{receiver.email.empty}")
    @Email(message = "{receiver.email.format}")
    @Column(name = "email")
    private String email;
}


