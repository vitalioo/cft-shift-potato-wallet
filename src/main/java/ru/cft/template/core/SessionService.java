package ru.cft.template.core;

import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;

import java.util.Optional;

public interface SessionService {
    Session create(User user);

    void save(Session session);

    void delete(Long id);

    Optional<Session> getById(Long id);
}
