package be.voupon.voupon.checkout;

import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.merchant.MerchantService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.voupon.Voupon;
import be.voupon.voupon.voupon.VouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {

    MerchantService merchantService;
    VouponService vouponService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setVouponService(VouponService vouponService) {
        this.vouponService = vouponService;
    }

    @PostMapping("/{pageHandle:^(?!merchant$).*}/checkout")
    public String showCheckoutOrderDetails(@ModelAttribute CheckoutDto checkoutDto, Model model){

        model.addAttribute("merchant", merchantService.getById(checkoutDto.getMid()));
        checkoutDto.setMerchant(merchantService.getById(checkoutDto.getMid()));

        model.addAttribute("voupon", vouponService.getById(checkoutDto.getVid()));
        checkoutDto.setVoupon(vouponService.getById(checkoutDto.getVid()));

        System.out.println(checkoutDto.getMerchant().getCompanyName());
        System.out.println(checkoutDto.getVoupon().getDescription());

        return "checkout/orderdetails";
    }

}
