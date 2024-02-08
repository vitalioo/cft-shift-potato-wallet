package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.template.api.exception.InvalidTokenException;
import ru.cft.template.api.exception.SessionNotFoundException;
import ru.cft.template.core.SessionService;
import ru.cft.template.core.repositories.SessionRepository;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session create(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setActive(true);
        session.setExpirationTimeIn30Minutes();

        save(session);

        return session;
    }

    @Override
    public void save(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public Optional<Session> getById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);

        if (session.isEmpty()) {
            log.error("Session not found");
            throw new SessionNotFoundException("Session not found");
        }

        return sessionRepository.findById(id);
    }

    @Override
    public Session getByUser(Optional<User> user, String authTokenHeader) {
        if (!authTokenHeader.startsWith("Bearer ")) {
            log.error("No token input");
            throw new InvalidTokenException("Invalid token input");
        }

        String authToken = authTokenHeader.substring("Bearer ".length());

        return user.get().getTokens().stream()
                .filter(session -> session.getId().equals(UUID.fromString(authToken)))
                .findFirst()
                .get().getSession();
    }
}
