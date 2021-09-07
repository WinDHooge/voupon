package be.voupon.voupon.checkout;

import be.voupon.voupon.customer.Customer;
import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.recipient.Recipient;
import be.voupon.voupon.voupon.Voupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Getter
@Setter
@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class CheckoutDto {

    private Merchant merchant;
    private Voupon voupon;
    private Customer customer;
    private Recipient recipient;

}
