package be.voupon.voupon.user;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

@PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("isAuthenticated()")
    User getUserByHandle(String handle);

    @PreAuthorize("isAuthenticated()")
    User getUserComplete(String handle);

    void follow(String userHandle,String toFollowHandle);

    void unFollow(String userHandle,String unFollowHandle);

    Set<User > getUsersIFollow(String handle);
    List<User> getUsersToFollow(String handle, Set<User> usersIFollow);

    void save(User user) throws UserServiceImpl.PasswordException, PasswordMisMatchException;

}
