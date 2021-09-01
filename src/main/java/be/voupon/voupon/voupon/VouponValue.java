package be.voupon.voupon.voupon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "vouponvalues")
public class VouponValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private int value;

    @Column(name = "minval")
    private int minValue;

    @Column(name = "maxval")
    private int maxValue;

    @ManyToOne
    @JoinColumn(name="voupon_id")
    Voupon voupon;

}
