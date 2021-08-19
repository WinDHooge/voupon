package be.voupon.voupon.merchant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface merchantRepository extends JpaRepository<Merchant, Integer> {
    Merchant findMerchantByEmail(String email);

}
