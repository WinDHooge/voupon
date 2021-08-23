package be.voupon.voupon.user;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface UserService {

    class PasswordException extends Exception {
        public PasswordException(String message) {
            super(message);
        }
    }

    class PasswordMisMatchException extends Exception {
        public PasswordMisMatchException(String message) {
            super(message);
        }
    }

    class UniqueUserException extends Exception {
        public UniqueUserException(String message) {
            super(message);
        }
    }

    User getUserByEmail(String email);

    void save(User user) throws UserServiceImpl.PasswordException, PasswordMisMatchException, UniqueUserException;

}
