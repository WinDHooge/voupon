package be.voupon.voupon.merchant;

import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MerchantController {

    private MerchantService merchantService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/account/merchant/overview")
    public String showOverview(Model model) {

        return "/account/merchant/overview";
    }

    @GetMapping("/account/merchant/add")
    public String showAdd(Model model) {
        model.addAttribute("merchant", new Merchant());

        return "/account/merchant/edit";
    }

    @GetMapping("/account/merchant/edit/{id}")
    public String showEdit(@PathVariable int id, Model model) {

        return "/account/merchant/edit";
    }

    @PostMapping("/account/merchant/edit")
    public String postAdd(@Valid @ModelAttribute Merchant merchant, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/account/merchant/edit";
        }

        merchantService.save(merchant);

        return "redirect:/account/merchant/overview";
    }

}
