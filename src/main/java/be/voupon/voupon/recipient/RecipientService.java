package be.voupon.voupon.recipient;

public interface RecipientService {

    void save(Recipient recipient);

    Recipient getRecipientByAllData(String email, String firstName, String lastName);
}
