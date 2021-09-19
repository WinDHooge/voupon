package be.voupon.voupon.order;

import be.voupon.voupon.merchant.Merchant;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void save(OrderDetail orderDetail);

    OrderDetail getOrderDetailByVouponCode(String vouponCode);

    List<Order> getOrdersByMerchant(Merchant merchant);

}
