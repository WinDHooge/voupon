package be.voupon.voupon.recipient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientServiceImpl implements RecipientService{

    private RecipientRepository recipientRepository;

    @Autowired
    public void setRecipientRepository(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Recipient getRecipientByAllData(String email, String firstName, String lastName) {
        return recipientRepository.findRecipientByRecipientEmailAndRecipientFirstNameAndRecipientLastName(email, firstName, lastName);
    }

    @Override
    public void save(Recipient recipient) {
        recipientRepository.save(recipient);
    }
}
