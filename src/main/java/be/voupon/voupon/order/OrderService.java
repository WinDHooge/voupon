package be.voupon.voupon.order;

public interface OrderService {

    void save(Order order);

    void save(OrderDetail orderDetail);

}
