package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.Session;
import ru.cft.template.api.model.Token;
import ru.cft.template.core.TokenService;
import ru.cft.template.core.repositories.TokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Token create(Session session) {
        Token token = new Token();
        token.setUser(session.getUser());
        token.setSession(session);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void delete(Long sessionId) {
        Token token = tokenRepository.findBySessionId(sessionId);
        tokenRepository.delete(token);
    }

    @Override
    public Optional<Token> getById(UUID id) {
        return tokenRepository.findById(id);
    }


}
