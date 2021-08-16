package be.voupon.voupon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserComplete(String email) {
        return null;
    }

    @Override
    public void save(User user) throws PasswordException, PasswordMisMatchException {
        if (user.getPassWord() != null && user.getPassWord().length() < 5) {
            throw new PasswordException("password malformed");
        }
        if (user.getPassWord() == null && user.getId() == 0) {
            throw new PasswordException("password malformed");
        }
        if (user.getPassWord().length() > 4 && !user.getPassWord().equals(user.getCheckPassWord()) ) {
            throw new PasswordMisMatchException("password mismatch");
        }

        if (user.getPassWord() == null && user.getId() > 0) {
            user.setPassWord(userRepository.findById(user.getId()).get().getPassWord());
        } else {
            user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
        }
        userRepository.save(user);
    }


}
