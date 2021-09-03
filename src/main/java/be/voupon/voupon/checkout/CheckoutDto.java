package be.voupon.voupon.checkout;

import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.voupon.Voupon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutDto {

    private int id;
    private int vid;
    private int mid;
    private Merchant merchant;
    private Voupon voupon;

    @Override
    public String toString() {
        return "CheckoutDto{" +
                "id=" + id +
                ", vid=" + vid +
                ", mid=" + mid +
                ", merchant=" + merchant +
                ", voupon=" + voupon +
                '}';
    }
}
