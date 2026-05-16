package ifaces;

import java.util.List;

import POJOS.Role;
import POJOS.User;

public interface UserManager {

    boolean register(User user);

    void createRole(Role role);

    void assignRole(User user, Role role);

    List<Role> getRoles();

    Role getRole(String name);

    User login(String username, String password);

    User getUser(String username, String email);

    void updateUser(User user, String newUsername);

    void updatePassword(User user, String newPassword);

    void deleteUser(User user);

    List<User> getAllUsers();

    void close();
}
