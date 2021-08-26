package be.voupon.voupon.voupon;

import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.text.AttributedString;

@Controller
public class VouponController {

    private UserService userService;
    private AttributedString model;
    private VouponService vouponService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVouponService(VouponService vouponService) {
        this.vouponService = vouponService;
    }


    @GetMapping("/account/voupons/overview")
    public String showVoupons(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        return "account/voupons/overview";
    }

    @GetMapping("/account/voupons/add")
    public String addVoupon(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user",user);

        Voupon voupon = new Voupon();
        model.addAttribute("voupon",voupon);

        return "account/voupons/edit";
    }
/*
    @GetMapping("/account/voupon/edit")
    public String showEdit(Model model) {

        return "/account/voupon/edit";
    }
*/

    @PostMapping("/account/voupons/edit")
    public String postEdit(@Valid @ModelAttribute Voupon voupon, Model model, BindingResult bindingResult, Principal principal, VouponService vouponService) {

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        if (bindingResult.hasErrors()) {
            return "redirect:/account/voupon/edit";
        }

        vouponService.save(voupon);


        return "redirect:/account/voupon/overview";
    }

}
