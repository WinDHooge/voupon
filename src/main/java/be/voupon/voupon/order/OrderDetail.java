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
@Table(name = "orderdetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{orderDetail.order}")
    @Column(name = "order")
    private int order;

    @NotBlank(message = "{orderDetail.voupon}")
    @Column(name = "voupon")
    private String voupon;

    @NotBlank(message = "{orderDetail.vouponCode}")
    @Column(name = "vouponCode")
    private String vouponCode;

    @NotBlank(message = "{orderDetail.unitPrice}")
    @Column(name = "unitPrice")
    private Boolean unitPrice;

    @NotBlank(message = "{orderDetail.quantity}")
    @Column(name = "quantity")
    private int quantity;

    @NotBlank(message = "{orderDetail.shipmentDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shipmentDate")
    private Date shipmentDate;


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order='" + order + '\'' +
                ", voupon='" + voupon + '\'' +
                ", vouponCode='" + vouponCode + '\'' +
                ", unitPrice=" + unitPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", sendDate='" + shipmentDate +
                '}';
    }



}
