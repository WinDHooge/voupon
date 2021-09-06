package be.voupon.voupon.merchant;

import be.voupon.voupon.order.Order;
import be.voupon.voupon.voupon.Voupon;
import lombok.Getter;
import lombok.Setter;
import be.voupon.voupon.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "merchants")
public class Merchant {

    public enum Country {
        ALBANIE("Albanië"), ANDORRA("Andorra"), ARMENIE("Armenië"), AZERBEIDZJAN("Azerbeidzjan"), BELGIE("Belgium"),
        BOSNIE_EN_HERZEGOVINA("Bosnië en Herzegovina"), BULGARIJE("Bulgarije"), CYPRUS("Cyprus"), DENEMARKEN("Denemarken"),
        DUITSLAND("Duitsland"), ESTLAND("Estland"), FINLAND("Finland"), FRANKRIJK("Frankrijk"), GEORGIE("Georgië"),
        GRIEKENLAND("Griekenland"), HONGARIJE("Hongarije"), IERLAND("Ierland"), IJSLAND("IJsland"), ITALIE("Italië"),
        KAZACHSTAN("Kazachstan"), KOSOVO("Kosovo"), KROATIE("Kroatië"), LETLAND("Letland"), LIECHTENSTEIN("Liechtenstein"),
        LITHOUWEN("Lithouwen"), LUXEMBURG("Luxemburg"), MALTA("Malta"), MOLDAVIE("Moldavië"),
        MONACO("Monaco"), MONTENEGRO("Montenegro"), NEDERLAND("Nederland"), NOORD_MACEDONIE("Noord-Macedonië"),
        NOORWEGEN("Noorwegen"), OEKRAINE("Oekraïne"), OOSTENRIJK("Oostenrijk"), POLEN("Polen"), PORTUGAL("Portugal"),
        ROEMENIE("Roemenië"), RUSLAND("Rusland"), SANMARINO("San Marino"), SERVIE("Servië"), SLOVANIE("Slovanië"),
        SLOWAKIJE("Slowakije"), SPANJE("Spanje"), TSJECHIÊ("Tsjechië"), TURKIJE("Turkije"), VATICAANSTAD("Vaticaanstad"),
        VERENIGD_KONINKRIJK("Verenigd Koninkrijk"), WIT_RUSLAND("Wit-Rusland"), ZWEDEN("Zweden"), ZWITSERLAND("Zwitserland");

        private final String displayName;

        Country(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{merchant.companyName.empty}")
    @Column(name = "companyName")
    private String companyName;

    @Size(min = 9, message = "{merchant.telephone.size}")
    @Pattern(regexp  = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "{merchant.telephone.format}")
    @Column(name = "telephone")
    private String telephone;

    @NotBlank(message = "{merchant.email.empty}")
    @Email(message = "{merchant.email.format}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{merchant.pageHandle.empty}")
    @Size(min = 2, message = "{merchant.pageHandle.size}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$", message = "{merchant.pageHandle.format}")
    private String pageHandle;

    @NotBlank(message = "{merchant.street}")
    @Column(name = "street")
    private String street;

    @NotBlank(message = "{merchant.number}")
    @Column(name = "number")
    private String number;

    @NotBlank(message = "{merchant.postalCode.empty}")
    @Size(min = 4, message = "{merchant.postalCode.size}")
    @Column(name = "postalCode")
    private String postalCode;

    @NotBlank(message = "{merchant.city}")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "{merchant.country}")
    @Column(name = "country")
    private String country;

    /*@NotNull(message = "value mismatch")
    @Column(name = "country")
    private Country country = Country.BELGIE;*/

    @NotBlank(message = "{merchant.VAT.empty}")
    @Size(min = 10, message = "{merchant.VAT.size}")
    @Column(name = "VAT")
    private String VAT;

    @Lob
    @Column(name = "companyDescription")
    private String companyDescription;

    @Lob
    @Column(name = "checkoutDescription")
    private String checkoutDescription;

    @NotBlank(message = "{merchant.paypalEmail.size}")
    @Email(message = "{merchant.paypalEmail.format}")
    @Column(name = "paypalEmail")
    private String paypalEmail;

    @ManyToMany
    @JoinTable(
            name = "user_merchant",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "merchant")
    private List<Order> orders;


    @OneToMany(mappedBy = "merchant")
    private List<Voupon> voupons;

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", pageHandle='" + pageHandle + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", VAT='" + VAT + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", checkoutDescription='" + checkoutDescription + '\'' +
                ", paypalEmail='" + paypalEmail + '\'' +
                ", users=" + users +
                ", voupons=" + voupons +
                '}';
    }
}
