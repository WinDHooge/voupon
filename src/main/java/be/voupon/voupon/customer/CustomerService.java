package be.voupon.voupon.customer;

public interface CustomerService {

    void save(Customer customer);

    Customer getCustomerByAllData(String email, String firstName, String lastName);
}
