package be.voupon.voupon.merchant;

import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MerchantController {

    @GetMapping("/account/merchant/overview")
    public String showOverview(Model model) {

        return "/account/merchant/overview";
    }

    @GetMapping("/account/merchant/add")
    public String showAdd(Model model) {

        return "/account/merchant/add";
    }

    @GetMapping("/account/merchant/edit")
    public String showEdit(Model model) {

        return "/account/merchant/edit";
    }

    @PostMapping("/account/merchant/add")
    public String postAdd(@Valid @ModelAttribute Merchant merchant, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/merchant";
        }
        return "redirect:/account";
    }

    @PostMapping("/account/merchant/edit")
    public String postEdit(@Valid @ModelAttribute Merchant merchant, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/merchant";
        }
        return "redirect:/account";
    }
}
