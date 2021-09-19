package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;

import java.util.List;

public interface VouponService {

    List<Voupon> getAll();

    List<Voupon> getMerchantVoupons(Merchant merchant);

    List<Voupon> getMerchantActiveVoupons(Merchant merchant);

    Voupon getById(Integer id);

    void save(Voupon voupon);

    void save(VouponValue vouponValue);

    void delete(int id);



}
