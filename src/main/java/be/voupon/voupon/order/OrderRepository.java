package be.voupon.voupon.order;

import be.voupon.voupon.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByMerchantOrderByDateDesc(Merchant merchant);

}
