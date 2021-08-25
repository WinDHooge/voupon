package be.voupon.voupon.voupon;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface VouponRepository {
        void save(Voupon voupon);


    public interface MerchantRepository extends JpaRepository<Voupon, Integer> {
        Voupon findVouponByValue(String value);
    }
}
