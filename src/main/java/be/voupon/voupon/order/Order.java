package be.voupon.voupon.order;

import be.voupon.voupon.voupon.Voupon;
import be.voupon.voupon.voupon.VouponValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{order.orderNumber}")
    @Column(name = "orderNumber")
    private int orderNumber;

    @NotBlank(message = "{order.date}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;

    @NotBlank(message = "{order.customer}")
    @Column(name = "customer")
    private String customer;

    @NotBlank(message = "{order.recipient}")
    @Column(name = "recipient")
    private String recipient;

    @OneToMany
    @JoinTable(
            name = "order_voupons",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "voupon_id"))
    private Set<Voupon> voupon;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", date='" + date + '\'' +
                ", customer='" + customer + '\'' +
                ", recipient=" + recipient +
                '}';
    }
}
