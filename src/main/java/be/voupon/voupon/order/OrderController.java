package be.voupon.voupon.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/redeem/{vouponCode:^(?!orderDetail$).*}")
    public String showRedeemVoupon(@PathVariable String vouponCode, Model model){
        OrderDetail orderDetail = orderService.getOrderDetailByVouponCode(vouponCode);

        model.addAttribute(orderDetail);

        return "account/redeem/redeem-voupon";
    }
}
