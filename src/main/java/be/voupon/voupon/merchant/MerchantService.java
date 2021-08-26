package be.voupon.voupon.merchant;


import java.util.List;

public interface MerchantService {
    List<Merchant> getAll();

    void save(Merchant merchant);
}
