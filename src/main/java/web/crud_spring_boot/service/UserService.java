package web.crud_spring_boot.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.crud_spring_boot.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User readUser(Long id);

    void createUser(User user);

    void editUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);
}
