package be.voupon.voupon.merchant;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{merchant.companyName}")
    @Size(min = 2, message = "{merchant.companyName}")
    @Column(name = "companyName")
    private String companyName;

    @NotBlank(message = "{merchant.telephone}")
    @Size(min = 9, message = "{merchant.telephone}")
    @Column(name = "telephone")
    private String telephone;

    @NotBlank(message = "{merchant.email}")
    @Email(message = "{merchant.email}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{merchant.pageHandle}")
    @Size(min = 2, message = "{merchant.pageHandle}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$")
    private String pageHandle;

    @NotBlank(message = "{merchant.street}")
    @Size(min = 5, message = "{merchant.street}")
    @Column(name = "street")
    private String street;

    @NotBlank(message = "{merchant.number}")
    @Size(min = 1, message = "{merchant.number}")
    @Column(name = "number")
    private String number;

    @NotBlank(message = "{merchant.postalCode}")
    @Size(min = 4, message = "{merchant.postalCode}")
    @Column(name = "postalCode")
    private String postalCode;

    @NotBlank(message = "{merchant.city}")
    @Size(min = 2, message = "{merchant.city}")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "{merchant.country}")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "{merchant.VAT}")
    @Size(min = 10, message = "{merchant.VAT}")
    @Column(name = "VAT")
    private String VAT;

    @NotBlank(message = "{merchant.companyDescription}")
    @Size(min = 20, message = "{merchant.companyDescription}")
    @Column(name = "companyDescription")
    private String companyDescription;

    @NotBlank(message = "{merchant.checkoutDescription}")
    @Size(min = 5, message = "{merchant.checkoutDescription}")
    @Column(name = "checkoutDescription")
    private String checkoutDescription;


    @NotBlank(message = "{merchant.paypalEmail}")
    @Email(message = "{merchant.paypalEmail}")
    @Column(name = "paypalEmail")
    private String paypalEmail;

    /*
    @NotBlank(message = "{merchant.user}")
    @Size(min = 2, message = "{merchant.user}")
    @Column(name = "user")
    private User user;
    */

}
