package be.voupon.voupon.voupon;

import java.util.List;

public interface VouponService {

    List<Voupon> getAll();

    Voupon getById(Integer id);

    void save(Voupon voupon);

    void delete(int id);



}
