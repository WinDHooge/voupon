package be.voupon.voupon.order;

import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.merchant.MerchantService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
public class OrderController {
    private OrderService orderService;
    private UserService userService;
    private MerchantService merchantService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/redeem/{vouponCode:^(?!orderDetail$).*}")
    public String showRedeemVoupon(@PathVariable String vouponCode, Model model, Principal principal){
        if(principal != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
        }else{
            return "redirect:/login";
        }

        OrderDetail orderDetail = orderService.getOrderDetailByVouponCode(vouponCode);
        model.addAttribute("orderDetail", orderDetail);


        /*Merchant merchant = merchantService.getById(id);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }*/


        return "account/redeem/redeem-voupon";
    }
}
