package be.voupon.voupon.voupon;

import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.merchant.MerchantService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VouponController {

    private UserService userService;
    private MerchantService merchantService;
    private VouponService vouponService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVouponService(VouponService vouponService) {
        this.vouponService = vouponService;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/account/voupons/overview")
    public String showOverview(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        List<Merchant> merchants = merchantService.getMyMerchants(user.getId());
        model.addAttribute("merchants",merchants);

        model.addAttribute("voupons",vouponService.getAll());

        return "account/voupons/overview";
    }

    @GetMapping("/account/voupons/merchant/{id}/overview")
    public String showMerchantVouponsOverview(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        Merchant merchant = merchantService.getById(id);
        model.addAttribute("merchant",merchant);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        model.addAttribute("voupons",vouponService.getMerchantVoupons(merchant));

        return "account/voupons/merchantvoupons";
    }

    @GetMapping("/account/voupons/merchant/{id}/add")
    public String showAddVouponToMerchant(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        Merchant merchant = merchantService.getById(id);
        model.addAttribute("merchant",merchant);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        Voupon voupon = new Voupon();
        VouponValue vouponValue = new VouponValue();
        List<VouponValue> vouponValues = new ArrayList<VouponValue>();
        vouponValues.add(vouponValue);
        voupon.setVouponValues(vouponValues);
        voupon.setMerchant(merchant);
        model.addAttribute("voupon", voupon);


        return "account/voupons/editmerchantvoupon";
    }

    @PostMapping("/account/voupons/editmerchantvoupon")
    public String postAddMerchantVoupon(@Valid @ModelAttribute Voupon voupon, Model model, BindingResult bindingResult, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Merchant merchant = merchantService.getById(voupon.merchant.getId());
        voupon.setMerchant(merchant);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        for(VouponValue vouponValue : voupon.getVouponValues()){
            if(vouponValue.getId() == 0){
                vouponValue.setVoupon(voupon);
                vouponService.save(vouponValue);
            }else if(vouponValue.getId() > 0){
                vouponValue.setId(vouponValue.getId());
                vouponValue.setVoupon(voupon);
                vouponService.save(vouponValue);
            }

        }

        if (bindingResult.hasErrors()) {
            return "redirect:/account/voupons/editmerchantvoupon";
        }

        vouponService.save(voupon);


        return "redirect:/account/voupons/merchant/" + voupon.merchant.getId() + "/overview";
    }

    @GetMapping("/account/voupons/merchant/{mid}/edit/{vid}")
    public String showEditMerchantVoupon(@PathVariable int mid, @PathVariable int vid, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Merchant merchant = merchantService.getById(mid);
        model.addAttribute("merchant",merchant);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        Voupon voupon = vouponService.getById(vid);
        if(voupon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        voupon.setMerchant(merchant);
        model.addAttribute("voupon", voupon);

        return "/account/voupons/editmerchantvoupon";
    }

    @GetMapping("/account/voupons/delete/{mid}/{id}")
    public String deleteMerchantVoupon(@PathVariable int id, @PathVariable int mid, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Merchant merchant = merchantService.getById(mid);
        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        Voupon voupon = vouponService.getById(id);
        if(voupon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }

        vouponService.delete(id);
        return "redirect:/account/voupons/merchant/" + mid + "/overview";
    }

}
