package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.core.UserService;
import ru.cft.template.core.repositories.UserRepository;
import ru.cft.template.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
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
        return userRepository.findByPhone(phone);
    }

    @Override
    public User getUserByIdWithSessions(Long id) {
        return userRepository.findByIdWithSessions(id);
    }

}