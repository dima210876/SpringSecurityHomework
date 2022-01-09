package my.studlabs.project.SpringSecurityHomework.service;

import my.studlabs.project.SpringSecurityHomework.model.User;

import java.util.List;
import java.util.Optional;

public interface SimpleUserService
{
    User register(User user);

    Optional<User> login(User user);

    Optional <User> getById(int id);

    List<User> getAll();

    User update(int id, User user);

    User changePassword(int id, String newPassword);

    void delete(int id);

    boolean isEmailUnique (String email);
}
