package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "voupons")
public class Voupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{voupon.name}")
    @Size(min = 5, message = "{voupon.name}")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotBlank(message = "{voupon.value}")
    @Size(min = 1, message = "{voupon.value}")
    @Column(name = "value")
    private int value;

    // Todo: Add date validation annotation
    @Column(name = "expireDate")
    private Date expireDate;

    @NotBlank(message = "{voupon.active}")
    @Column(name = "active")
    private boolean active = true;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "merchant_voupon",
            joinColumns = @JoinColumn(name = "voupon_id"),
            inverseJoinColumns = @JoinColumn(name = "voupon_id"))
    private Set<Voupon> voupons;

}
