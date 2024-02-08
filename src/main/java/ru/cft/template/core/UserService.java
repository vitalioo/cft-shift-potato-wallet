package ru.cft.template.core;

import ru.cft.template.entity.Session;
import ru.cft.template.entity.Token;
import ru.cft.template.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    void save(User user);

    void update(Long id, User user);

    User findByUsername(String name);

    User getUserByPhone(Long phone);

    User getUserByIdWithSessions(Long id);

    void saveSessionAndToken(User user, Session session, Token token);

    void deleteSession(User user, Optional<Session> session);
}
