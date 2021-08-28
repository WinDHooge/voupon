package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;

import java.util.List;

public interface VouponService {
    List<Voupon> getAll();

    Voupon getById(Integer id);

    //List<Voupon> getMyVoupons(Integer userId);

    void save(Voupon voupon);

    void delete(int id);

}
