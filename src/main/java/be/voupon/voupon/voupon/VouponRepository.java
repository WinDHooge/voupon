package be.voupon.voupon.voupon;


import be.voupon.voupon.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VouponRepository extends JpaRepository<Voupon, Integer> {

    Voupon findVouponById(Integer id);

    List<Voupon> findVouponByUsers_Id(Integer id);


}

