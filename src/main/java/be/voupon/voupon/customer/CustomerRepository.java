package be.voupon.voupon.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerEmailAndCustomerFirstNameAndCustomerLastName(String email, String firstName, String lastName);
}
