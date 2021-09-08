package be.voupon.voupon.customer;

import be.voupon.voupon.order.Order;
import be.voupon.voupon.recipient.Recipient;
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
    @NotBlank(message = "{customer.firstName.empty}")
    @Size(max = 50, message= "{customer.firstName.size}")
    private String customerFirstName;

    @NotBlank(message= "{customer.lastName.empty}")
    @Size(max = 50, message="{customer.lastName.size}")
    @Column(name = "lastName")
    private String customerLastName;

    @NotBlank(message = "{customer.email.empty}")
    @Email(message = "{customer.email.format}")
    @Column(name = "email")
    private String customerEmail;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + customerFirstName + '\'' +
                ", lastName='" + customerLastName + '\'' +
                ", email='" + customerEmail +
                '}';
    }
}
