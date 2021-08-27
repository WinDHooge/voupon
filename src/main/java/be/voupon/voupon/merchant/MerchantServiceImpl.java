package be.voupon.voupon.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    private MerchantRepository merchantRepository;

    @Autowired
    public void setMerchantRepository(MerchantRepository merchantRepository)
    {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

    @Override
    public List<Merchant> getMyMerchants(Integer userId) {
        return merchantRepository.findMerchantsByUsers_Id(userId);
    }

    public Merchant getMerchantByEmail(String email) {

        return merchantRepository.findMerchantByEmail(email);
    }



    public void save(Merchant merchant)  {

        merchantRepository.save(merchant);
    }
}
