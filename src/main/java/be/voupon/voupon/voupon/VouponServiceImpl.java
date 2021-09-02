package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VouponServiceImpl implements VouponService{

    private VouponRepository vouponRepository;
    private VouponValueRepository vouponValueRepository;

    @Autowired
    public void setVouponRepository(VouponRepository vouponRepository)
    {
        this.vouponRepository = vouponRepository;
    }

    @Autowired
    public void setVouponValueRepository(VouponValueRepository vouponValueRepository) {
        this.vouponValueRepository = vouponValueRepository;
    }

    @Override
    public List<Voupon> getAll() {
        return vouponRepository.findAll();
    }

    @Override
    public List<Voupon> getMerchantVoupons(Merchant merchant) {
        return vouponRepository.findVouponsByMerchant(merchant);
    }

    @Override
    public Voupon getById(Integer id) {
        return vouponRepository.findVouponById(id);
    }

    @Override
    public void delete(int id) {
        try {
            vouponRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void save(Voupon voupon)  {
        vouponRepository.save(voupon);
    }

    public void save(VouponValue vouponValue)  {
        vouponValueRepository.save(vouponValue);
    }
}
