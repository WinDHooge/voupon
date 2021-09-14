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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

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

            OrderDetail orderDetail = orderService.getOrderDetailByVouponCode(vouponCode);
            if(orderDetail == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
            }else{
                model.addAttribute("orderDetail", orderDetail);

                // Get Merchant & check if user is allowed to act
                Merchant merchant = orderDetail.getOrder().getMerchant();
                if(merchant == null || !user.getMerchants().contains(merchant)){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
                }else{
                    model.addAttribute("merchant", merchant);
                }
            }

        }else{
            return "redirect:/login";
        }

        return "account/redeem/redeem-voupon";
    }

    @PostMapping("/redeem/{vouponCode:^(?!orderDetail$).*}")
    public String redeemVoupon(@ModelAttribute OrderDetail orderDetail, Model model, Principal principal){
        if(principal != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);

            OrderDetail updateOrderDetail = orderService.getOrderDetailByVouponCode(orderDetail.getVouponCode());

            if(updateOrderDetail == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
            }else{
                // Get Merchant & check if user is allowed to act
                Merchant merchant = updateOrderDetail.getOrder().getMerchant();
                if(merchant == null || !user.getMerchants().contains(merchant)){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
                }else{

                    // Change redeemed status
                    updateOrderDetail.setRedeemed(true);
                    orderService.save(updateOrderDetail);

                }
            }

        }else{
            return "redirect:/login";
        }

        return "redirect:/redeem/" + orderDetail.getVouponCode();
    }

}
