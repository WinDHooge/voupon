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
import java.util.List;
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
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "theme")
    private String theme;

    //@NotBlank(message = "{voupon.value}")
    //@Size(min = 1, message = "{voupon.value}")
    //@Column(name = "value")
    //private int value;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expireDate")
    private Date expireDate;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name="merchant_id")
    Merchant merchant;

    @OneToMany(mappedBy = "voupon")
    private List<VouponValue> vouponValues;

    @Override
    public String toString() {
        return "Voupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", expireDate=" + expireDate +
                ", active=" + active +
                ", merchant=" + merchant +
                ", vouponValues=" + vouponValues +
                '}';
    }
}
