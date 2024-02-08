package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.api.exception.UserNotFoundException;
import ru.cft.template.core.UserService;
import ru.cft.template.core.repositories.UserRepository;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.Token;
import ru.cft.template.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByFirstName(name);
    }

    @Override
    public User getUserByPhone(Long phone) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User getUserByIdWithSessions(Long id) {
        User user = userRepository.findByIdWithSessions(id);
        if (user == null) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void saveSessionAndToken(User user, Session session, Token token) {
        user.getSessions().add(session);
        user.getTokens().add(token);
    }

    @Override
    public void deleteSession(User user, Optional<Session> session) {
        user.getSessions().remove(session);
    }


}