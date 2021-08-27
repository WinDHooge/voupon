package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;

import java.util.List;

public interface VouponService {
    List<Voupon> getAll();

    void save(Voupon voupon);
}
