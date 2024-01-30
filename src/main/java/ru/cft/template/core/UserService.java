package ru.cft.template.core;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.cft.template.models.User;

public interface UserService extends UserDetailsService {
    User getUserById(String id);
}
