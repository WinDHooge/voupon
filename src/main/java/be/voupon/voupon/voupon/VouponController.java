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

    @GetMapping("/account/voupons/add")
    public String showAdd(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        model.addAttribute("voupon", new Voupon());


        return "account/voupons/edit";
    }

    @GetMapping("/account/voupon/delete/{id}")
    public String delete(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Voupon voupon = vouponService.getById(id);
        if(voupon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        vouponService.delete(id);
        return "redirect:/account/voupons/overview";
    }

    @GetMapping("/account/voupon/edit/{id}")
    public String showEdit(@PathVariable int id, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Voupon voupon = vouponService.getById(id);
        if(voupon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("voupon", voupon);

        return "/account/voupons/edit";
    }

    @PostMapping("/account/voupons/edit")
    public String postAdd(@Valid @ModelAttribute Voupon voupon, Model model, BindingResult bindingResult, Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        if (bindingResult.hasErrors()) {
            return "redirect:/account/voupons/edit";
        }

        vouponService.save(voupon);


        return "redirect:/account/voupons/overview";
    }

    @GetMapping("/account/voupons/merchant/{id}/overview")
    public String showMerchantVouponsOverview(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        Merchant merchant = merchantService.getById(id);
        model.addAttribute("merchant",merchant);

        model.addAttribute("voupons",vouponService.getAll());

        return "account/voupons/merchantvoupons";
    }

    @GetMapping("/account/voupons/merchant/{id}/add")
    public String showAddVouponToMerchant(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        Merchant merchant = merchantService.getById(id);
        model.addAttribute("merchant",merchant);

        Voupon voupon = new Voupon();
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
        //System.out.println(voupon.merchant.toString());

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

        Voupon voupon = vouponService.getById(vid);
        if(voupon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        voupon.setMerchant(merchant);
        model.addAttribute("voupon", voupon);

        return "/account/voupons/editmerchantvoupon";
    }


}
