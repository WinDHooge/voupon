package be.voupon.voupon.merchant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    Merchant findMerchantByEmail(String email);

}
