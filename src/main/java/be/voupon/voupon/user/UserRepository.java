package be.voupon.voupon.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    User findUserByName(String firstName);

}
