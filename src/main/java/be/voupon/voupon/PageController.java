package be.voupon.voupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    @GetMapping("/pricing")
    public String showPricing(){
        return "pricing";
    }

    @GetMapping("/faq")
    public String showFaq(){
        return "faq";
    }

}
