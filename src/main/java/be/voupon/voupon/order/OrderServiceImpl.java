package be.voupon.voupon.order;

import be.voupon.voupon.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailByVouponCode(String vouponCode) {
        return orderDetailRepository.findOrderDetailByVouponCode(vouponCode);
    }

    @Override
    public List<Order> getOrdersByMerchant(Merchant merchant) {
        return orderRepository.findAllByMerchantOrderByDateDesc(merchant);
    }
}
