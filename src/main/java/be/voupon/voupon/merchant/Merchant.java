package be.voupon.voupon.merchant;

import lombok.Getter;
import lombok.Setter;
import be.voupon.voupon.user.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "merchants")
public class Merchant {

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

}
