package be.voupon.voupon.recipient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
    Recipient findRecipientByRecipientEmailAndRecipientFirstNameAndRecipientLastName(String email, String firstName, String lastName);
}
