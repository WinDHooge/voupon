package be.voupon.voupon.merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    Merchant findMerchantByEmail(String email);

    List<Merchant> findMerchantsByUsers_Id(Integer id);
}
