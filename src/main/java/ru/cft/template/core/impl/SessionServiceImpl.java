package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.core.SessionService;
import ru.cft.template.core.repositories.SessionRepository;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session create(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setActive(true);
        session.setExpirationTimeIn30Minutes();
        sessionRepository.save(session);

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
        return sessionRepository.findById(id);
    }
}
