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
import java.util.HashSet;
import java.util.Set;

@Controller
public class MerchantController {

    private MerchantService merchantService;
    private UserService userService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account/merchant/overview")
    public String showOverview(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("merchants",merchantService.getMyMerchants(user.getId()));

        return "/account/merchant/overview";
    }

    @GetMapping("/account/merchant/add")
    public String showAdd(Model model, Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("merchant", new Merchant());

        return "/account/merchant/edit";
    }

    @GetMapping("/account/merchant/edit/{id}")
    public String showEdit(@PathVariable int id, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/account/merchant/edit";
    }

    @PostMapping("/account/merchant/edit")
    public String postAdd(@Valid @ModelAttribute Merchant merchant, BindingResult bindingResult, Model model, Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        if (bindingResult.hasErrors()) {
            return "account/merchant/edit";
        }

        try {
            if(merchant.getUsers() == null){
                merchant.setUsers(new HashSet<User>());
            }
            merchant.getUsers().add(user);

            merchantService.save(merchant);
        } catch(Exception e){
            System.out.println(e);
        }

        return "redirect:/account/merchant/overview";
    }

}
