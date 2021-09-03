package be.voupon.voupon.recipient;

import be.voupon.voupon.voupon.Voupon;
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
@Table(name= "recipients")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    @NotBlank(message = "{recipient.firstName}")
    @Size(max = 50, message= "{recipient.firstName.size}")
    private String firstName;

    @NotBlank(message= "{recipient.lastName}")
    @Size(max = 50, message="{recipient.lastName.size}")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "{recipient.email.empty}")
    @Email(message = "{recipient.email.format}")
    @Column(name = "email")
    private String email;

    @OneToMany
    @JoinTable(
            name = "recipient_voupons",
            joinColumns = @JoinColumn(name = "recipient_id"),
            inverseJoinColumns = @JoinColumn(name = "voupon_id"))
    private Set<Voupon> voupon;

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +
                '}';
    }
}


