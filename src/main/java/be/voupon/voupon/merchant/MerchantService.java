package be.voupon.voupon.merchant;


import java.util.List;

public interface MerchantService {
    List<Merchant> getAll();

    List<Merchant> getMyMerchants(Integer userId);

    void save(Merchant merchant);
}
