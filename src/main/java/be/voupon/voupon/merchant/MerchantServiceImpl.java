package be.voupon.voupon.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    private MerchantRepository merchantRepository;

    @Autowired
    public void setMerchantRepository(MerchantRepository merchantRepository)
    {
        this.merchantRepository = merchantRepository;
    }



    public Merchant getMerchantByEmail(String email) {

        return merchantRepository.findMerchantByEmail(email);
    }



    public void save(Merchant merchant)  {

        merchantRepository.save(merchant);
    }
}
