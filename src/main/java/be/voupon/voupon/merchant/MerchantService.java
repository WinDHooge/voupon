package be.voupon.voupon.merchant;


import java.util.List;

public interface MerchantService {
    List<Merchant> getAll();

    Merchant getById(Integer id);

    List<Merchant> getMyMerchants(Integer userId);

    void save(Merchant merchant);
}
