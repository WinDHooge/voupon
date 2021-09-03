package be.voupon.voupon.customer;

import be.voupon.voupon.user.User;
import be.voupon.voupon.voupon.Voupon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name= "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    @NotBlank(message = "{customer.firstName}")
    @Size(max = 50, message= "{customer.firstName.size}")
    private String firstName;

    @NotBlank(message= "{customer.lastName}")
    @Size(max = 50, message="{customer.lastName.size}")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "{customer.email.empty}")
    @Email(message = "{customer.email.format}")
    @Column(name = "email")
    private String email;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +
                '}';
    }
}
