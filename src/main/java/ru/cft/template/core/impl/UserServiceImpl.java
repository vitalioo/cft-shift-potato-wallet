package ru.cft.template.core.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cft.template.api.security.UserDetailsImpl;
import ru.cft.template.core.UserService;
import ru.cft.template.core.repositories.UserRepository;
import ru.cft.template.models.User;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByFirstName(username));

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserDetailsImpl(user.get());
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getReferenceById(UUID.fromString(id));
    }
}
