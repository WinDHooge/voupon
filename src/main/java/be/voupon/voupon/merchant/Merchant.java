package be.voupon.voupon.merchant;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{merchant.companyName}")
    @Size(min = 2, message = "{merchant.companyName}")
    @Column(name = "companyName")
    private String companyName;

    @NotBlank(message = "{merchant.pageHandle}")
    @Size(min = 3, message = "{merchant.pageHandle}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$")
    private String pageHandle;

    @NotBlank(message = "{merchant.country}")
    @Size(min = 5, message = "{merchant.country}")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "{merchant.postalCode}")
    @Size(min = 4, message = "{merchant.postalCode}")
    @Column(name = "postalCode")
    private int postalCode;

    @NotBlank(message = "{merchant.paypalEmail}")
    @Email(message = "{merchant.paypalEmail}")
    @Column(name = "paypalEmail")
    private String paypalEmail;

    @NotBlank(message = "{merchant.user}")
    @Size(min = 2, message = "{merchant.user}")
    @Column(name = "user")
    private String user;


}
