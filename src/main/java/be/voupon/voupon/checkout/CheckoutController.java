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
    private final CheckoutDto checkoutDto;

    public CheckoutController(CheckoutDto checkoutDto) {
        this.checkoutDto = checkoutDto;
    }

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
    public String showCheckoutOrderDetails(@ModelAttribute Voupon voupon, Model model){

        Voupon chosenVoupon = vouponService.getById(voupon.getId());

        checkoutDto.setVoupon(chosenVoupon);
        checkoutDto.setMerchant(chosenVoupon.getMerchant());

        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/orderdetails";
    }

    @GetMapping("/{pageHandle:^(?!merchant$).*}/checkout/orderdetails")
    public String showCheckoutOrderDetailsStep(Model model){
        model.addAttribute(checkoutDto);
        return "checkout/orderdetails";
    }
}
