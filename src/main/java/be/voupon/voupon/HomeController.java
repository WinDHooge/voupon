package be.voupon.voupon;

import be.voupon.voupon.user.User;
import be.voupon.voupon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String showHome(Model model, Principal principal, HttpServletRequest httpServletRequest){
        if(principal != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        return "home";
    }

    @GetMapping
    public String showAccount(Model model, Principal principal) {
        String acc = principal.getName();

        model.addAttribute("user", userService.getUserByEmail(acc));

        return "account";
    }
}
