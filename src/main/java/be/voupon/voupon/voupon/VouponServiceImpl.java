package be.voupon.voupon.voupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VouponServiceImpl implements VouponService{

    private VouponRepository vouponRepository;

    @Autowired
    public void setMerchantRepository(VouponRepository vouponRepository)
    {
        this.vouponRepository = vouponRepository;
    }

    public void save(Voupon voupon)  {

        vouponRepository.save(voupon);
    }
}
