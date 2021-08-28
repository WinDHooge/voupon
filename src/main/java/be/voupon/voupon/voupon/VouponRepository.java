package be.voupon.voupon.voupon;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VouponRepository extends JpaRepository<Voupon, Integer> {

    Voupon findVouponById(Integer id);


    void editById(int id);



    //List<Voupon> findVouponsByUsers_Id(Integer userId);


}

