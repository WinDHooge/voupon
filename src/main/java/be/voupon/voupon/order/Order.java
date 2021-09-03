package be.voupon.voupon.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

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
}
