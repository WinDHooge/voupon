package be.voupon.voupon.merchant;

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

        Merchant merchant = merchantService.getById(id);

        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("merchant", merchant);

        return "/account/merchant/edit";
    }

    @GetMapping("/account/merchant/delete/{id}")
    public String delete(@PathVariable int id, Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        Merchant merchant = merchantService.getById(id);
        if(merchant == null || !user.getMerchants().contains(merchant)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        merchantService.delete(id);
        return "redirect:/account/merchant/overview";
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
            // Add new merchant
            if(merchant.getId() == 0){
                merchant.getUsers().add(user);
                merchantService.save(merchant);
            }
            // Edit existing Merchant & check if allowed
            else if(merchant.getId() > 0 && user.getMerchants().contains(merchant)){
                merchantService.save(merchant);
            }
        } catch(Exception e){
            System.out.println(e);
        }

        return "redirect:/account/merchant/overview";
    }

    @GetMapping("/{pageHandle:^(?!merchant$).*}")
    public String showMerchantFrontend(@PathVariable String pageHandle, Model model){
        Merchant merchant = merchantService.getMerchantByPageHandle(pageHandle);
        if(merchant == null){
            return "redirect:/";
        }
        model.addAttribute("merchant",merchant);
        return "merchantfrontend";
    }

}
