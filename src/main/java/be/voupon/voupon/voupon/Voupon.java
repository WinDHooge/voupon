package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.management.Notification;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "voupons")
public class Voupon  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{voupon.name}")
    @Size(min = 5, message = "{voupon.name}")
    @Column(name = "name")
    private String name;

    @Size(min = 5, message = "{voupon.description}")
    @Column(name = "description")
    private String description;

    //@NotBlank(message = "{voupon.value}")
    //@Size(min = 1, message = "{voupon.value}")
    @Column(name = "value")
    private int value;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expireDate")
    private Date expireDate;

    //@NotBlank(message = "{voupon.active}")
    @Column(name = "active")
    private boolean active = true;
    /*
    @NotBlank(message = "{voupon.pageHandle.empty}")
    @Size(min = 2, message = "{voupon.pageHandle.size}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$", message = "{voupon.pageHandle.format}")
    private String pageHandle;
    */

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "merchant_voupon",
            joinColumns = @JoinColumn(name = "voupon_id"),
            inverseJoinColumns = @JoinColumn(name = "voupon_id"))
    private Set<Voupon> voupons;

}
