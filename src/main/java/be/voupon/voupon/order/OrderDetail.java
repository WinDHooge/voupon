package be.voupon.voupon.order;

import be.voupon.voupon.customer.Customer;
import be.voupon.voupon.recipient.Recipient;
import be.voupon.voupon.voupon.Voupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "orderdetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="voupon_id")
    private Voupon voupon;

    @ManyToOne
    @JoinColumn(name="recipient_id")
    private Recipient recipient;

    @Column(name = "vouponCode")
    private String vouponCode;

    @Column(name = "unitPrice")
    private int unitPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shipmentDate")
    private Date shipmentDate;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "redeemed")
    private boolean redeemed;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", vouponCode='" + vouponCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", shipmentDate=" + shipmentDate +
                '}';
    }
}
