package be.voupon.voupon.voupon;


import org.springframework.data.jpa.repository.JpaRepository;

       public interface VouponRepository extends JpaRepository<Voupon, Integer> {

           //Voupon findVouponByName(String name);
    }

