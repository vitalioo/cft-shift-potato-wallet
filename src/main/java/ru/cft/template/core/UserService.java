package ru.cft.template.core;

import ru.cft.template.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    void save(User user);

    void update(Long id, User user);

    User findByUsername(String name);

    User getUserByPhone(Long phone);

    User getUserByIdWithSessions(Long id);
}
