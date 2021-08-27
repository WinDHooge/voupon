package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VouponServiceImpl implements VouponService{

    private VouponRepository vouponRepository;

    @Autowired
    public void setVouponRepository(VouponRepository vouponRepository)
    {
        this.vouponRepository = vouponRepository;
    }

    @Override
    public List<Voupon> getAll() {

        return vouponRepository.findAll();
    }

/*
    public Voupon getVouponByName(String name) {

        return vouponRepository.findVouponByName(name);
    }
*/
    public void save(Voupon voupon)  {

        vouponRepository.save(voupon);
    }
}
