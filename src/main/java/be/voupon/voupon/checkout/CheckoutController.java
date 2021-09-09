package be.voupon.voupon.checkout;

import be.voupon.voupon.customer.Customer;
import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.merchant.MerchantService;
import be.voupon.voupon.recipient.Recipient;
import be.voupon.voupon.user.User;
import be.voupon.voupon.voupon.Voupon;
import be.voupon.voupon.voupon.VouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {
    private final CheckoutDto checkoutDto;

    public CheckoutController(CheckoutDto checkoutDto) {
        this.checkoutDto = checkoutDto;
    }

    MerchantService merchantService;
    VouponService vouponService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setVouponService(VouponService vouponService) {
        this.vouponService = vouponService;
    }

    @PostMapping("/{pageHandle:^(?!merchant$).*}/checkout")
    public String showCheckoutOrderDetails(@ModelAttribute Voupon voupon, Model model){

        Voupon chosenVoupon = vouponService.getById(voupon.getId());

        checkoutDto.setVoupon(chosenVoupon);
        checkoutDto.setMerchant(chosenVoupon.getMerchant());

        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/orderdetails";
    }

    @GetMapping("/{pageHandle:^(?!merchant$).*}/checkout/orderdetails")
    public String showCheckoutOrderDetailsStep(Model model){
        model.addAttribute("checkoutDto", checkoutDto); // @Todo:necessary?

        // Create new Recipient & add to model
        if(checkoutDto.getRecipient() == null){
            Recipient newRecipient = new Recipient();
            model.addAttribute("recipient",newRecipient);
        }else{
            model.addAttribute("recipient",checkoutDto.getRecipient());
        }

        // Create new Customer & add to model
        if(checkoutDto.getCustomer() == null){
            Customer newCustomer = new Customer();
            model.addAttribute("customer",newCustomer);
        }else{
            model.addAttribute("customer",checkoutDto.getCustomer());
        }


        return "checkout/orderdetails";
    }

    @PostMapping(value = "/{pageHandle:^(?!merchant$).*}/checkout/orderdetails", params ="previous")
    public String backToStepOne(){
        return "redirect:/" + checkoutDto.getMerchant().getPageHandle();
    }

    @PostMapping(value = "/{pageHandle:^(?!merchant$).*}/checkout/orderdetails", params ="next")
    public String updateOrderDetails(@Valid @ModelAttribute Recipient recipient, BindingResult bindingResultRecipient, @Valid @ModelAttribute Customer customer, BindingResult bindingResultCustomer, Model model){
        //--model.addAttribute("checkoutDto", checkoutDto);
        //--System.out.println(checkoutDto.getVoupon());

        // Validate & retrieve the form ModelAttributes
        if (bindingResultRecipient.hasErrors() || bindingResultCustomer.hasErrors()) {
            model.addAttribute(checkoutDto);
            return "/checkout/orderdetails";
        }

        // Add them to the proxy checkoutDto object
        checkoutDto.setCustomer(customer);
        checkoutDto.setRecipient(recipient);
        model.addAttribute(checkoutDto);

        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/ordersummary";
    }

    // Create the GetMapping for "/{pageHandle:^(?!merchant$).*}/checkout/ordersummary"
    // Add the checkoutDto to the Model
    // Create new Order object & add to model
    // Create new OrderDetail object & add to model
    @GetMapping("/{pageHandle:^(?!merchant$).*}/checkout/ordersummary")
    public String showCheckoutOrderSummaryStep(Model model){
        model.addAttribute("checkoutDto", checkoutDto);

        return "checkout/ordersummary";
    }

    @PostMapping(value = "/{pageHandle:^(?!merchant$).*}/checkout/ordersummary", params ="previous")
    public String backToStepTwo(){
        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/orderdetails";
    }

    @PostMapping(value = "/{pageHandle:^(?!merchant$).*}/checkout/ordersummary", params ="next")
    public String showCheckoutConfirmationStep(Model model){
        model.addAttribute(checkoutDto);
        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/orderconfirmation";
    }

    @GetMapping("/{pageHandle:^(?!merchant$).*}/checkout/orderconfirmation")
    public String showCheckoutOrderConfirmationStep(Model model){
        model.addAttribute("checkoutDto", checkoutDto);

        return "checkout/orderconfirmation";
    }

}
