package be.voupon.voupon.customer;

import be.voupon.voupon.order.Order;
import be.voupon.voupon.recipient.Recipient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @OneToMany
    @JoinTable(
            name = "customer_order",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders;

    @OneToMany
    @JoinTable(
            name = "customer_recipient",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    private Set<Recipient> recipient;


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
