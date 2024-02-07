package ru.cft.template.core;

import ru.cft.template.entity.Session;
import ru.cft.template.entity.Token;

import java.util.Optional;
import java.util.UUID;

public interface TokenService {
    Token create(Session session);

    void save(Token token);

    void delete(Long sessionId);

    Optional<Token> getById(UUID id);
}
