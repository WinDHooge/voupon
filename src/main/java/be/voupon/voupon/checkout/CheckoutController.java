package be.voupon.voupon.checkout;

import be.voupon.voupon.customer.Customer;
import be.voupon.voupon.customer.CustomerService;
import be.voupon.voupon.email.EmailService;
import be.voupon.voupon.email.EmailServiceImpl;
import be.voupon.voupon.merchant.Merchant;
import be.voupon.voupon.merchant.MerchantService;
import be.voupon.voupon.order.Order;
import be.voupon.voupon.order.OrderDetail;
import be.voupon.voupon.order.OrderService;
import be.voupon.voupon.recipient.Recipient;
import be.voupon.voupon.recipient.RecipientService;
import be.voupon.voupon.user.User;
import be.voupon.voupon.voupon.Voupon;
import be.voupon.voupon.voupon.VouponService;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class CheckoutController<OrderDetailService> {
    private final CheckoutDto checkoutDto;
    final MessageDigest digest = MessageDigest.getInstance("SHA3-256");

    public CheckoutController(CheckoutDto checkoutDto) throws NoSuchAlgorithmException {
        this.checkoutDto = checkoutDto;
    }

    MerchantService merchantService;
    VouponService vouponService;
    CustomerService customerService;
    RecipientService recipientService;
    OrderService orderService;
    EmailService emailService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setVouponService(VouponService vouponService) {
        this.vouponService = vouponService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setRecipientService(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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
        model.addAttribute("checkoutDto", checkoutDto);

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

        // Validate & retrieve the form ModelAttributes
        if (bindingResultRecipient.hasErrors() || bindingResultCustomer.hasErrors()) {
            model.addAttribute(checkoutDto);
            return "/checkout/orderdetails";
        }

        // Add them to the proxy checkoutDto object
        Customer existingCustomer = customerService.getCustomerByAllData(customer.getCustomerEmail(), customer.getCustomerFirstName(), customer.getCustomerLastName());
        checkoutDto.setCustomer(customer);
        if(existingCustomer != null){
            checkoutDto.getCustomer().setId(existingCustomer.getId());
        }

        Recipient existingRecipient = recipientService.getRecipientByAllData(recipient.getRecipientEmail(), recipient.getRecipientFirstName(), recipient.getRecipientLastName());
        checkoutDto.setRecipient(recipient);
        if(existingRecipient != null){
            checkoutDto.getRecipient().setId(existingRecipient.getId());
        }


        model.addAttribute(checkoutDto);

        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/ordersummary";
    }

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
    public String showCheckoutConfirmationStep(Model model) throws MessagingException {

        // Create new Order object & add to model
        checkoutDto.setOrder(new Order());
        checkoutDto.getOrder().setDate(new Date());
        checkoutDto.getOrder().setCustomer(checkoutDto.getCustomer());
        checkoutDto.getOrder().setMerchant(checkoutDto.getMerchant());
        // Create OrderDetail
        OrderDetail newOrderDetail = new OrderDetail();
        newOrderDetail.setOrder(checkoutDto.getOrder());
        newOrderDetail.setVoupon(checkoutDto.getVoupon());
        newOrderDetail.setRecipient(checkoutDto.getRecipient());

        newOrderDetail.setUnitPrice(checkoutDto.getVoupon().getVouponValues().get(0).getValue());
        newOrderDetail.setShipmentDate(new Date());

        // Add orderdetail to order
        List<OrderDetail> newOrderDetailList = new ArrayList<OrderDetail>();
        newOrderDetailList.add(newOrderDetail);
        checkoutDto.getOrder().setOrderDetails(newOrderDetailList);

        customerService.save(checkoutDto.getCustomer());
        recipientService.save(checkoutDto.getRecipient());
        orderService.save(checkoutDto.getOrder());
        orderService.save(newOrderDetail);

        // Create unique hashed vouponcode for each coupon
        for(OrderDetail od : checkoutDto.getOrder().getOrderDetails()){
            String originalString = od.getId() + "*IAmASaltKeyDinges";
            byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            od.setVouponCode(bytesToHex(hashbytes));
            orderService.save(od);
        }

        model.addAttribute(checkoutDto);

        // Send emails to R & C
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customerFirstname", checkoutDto.getCustomer().getCustomerFirstName());
        templateModel.put("customerLastname", checkoutDto.getCustomer().getCustomerLastName());
        templateModel.put("customerEmail", checkoutDto.getCustomer().getCustomerEmail());
        templateModel.put("vouponlogo", "vouponlogo");
        templateModel.put("emailtemplate", "email/order.html");

        emailService.sendMessageUsingThymeleafTemplate(
                checkoutDto.getRecipient().getRecipientEmail(),
                "Welcome to Voupon",
                templateModel);


        return "redirect:/" + checkoutDto.getMerchant().getPageHandle() + "/checkout/orderconfirmation";
    }

    @GetMapping("/{pageHandle:^(?!merchant$).*}/checkout/orderconfirmation")
    public String showCheckoutOrderConfirmationStep(Model model){
        model.addAttribute("checkoutDto", checkoutDto);

        return "checkout/orderconfirmation";
    }
    @PostMapping(value = "/{pageHandle:^(?!merchant$).*}/checkout/orderconfirmation", params ="next")
    public String backToMerchantDetail(){
        return "redirect:/" + checkoutDto.getMerchant().getPageHandle();
    }

}
