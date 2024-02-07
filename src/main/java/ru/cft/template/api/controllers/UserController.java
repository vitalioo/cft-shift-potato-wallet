package ru.cft.template.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.model.account.LoginRequest;
import ru.cft.template.api.model.auth.SessionResponse;
import ru.cft.template.core.SessionService;
import ru.cft.template.core.TokenService;
import ru.cft.template.core.UserService;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.Token;
import ru.cft.template.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;
    private final TokenService tokenService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getInfo(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);

            if (user.isEmpty()) {
                log.error("User not found");
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody User user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body("Registration failed");
            }
            userService.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionResponse>> getSessions(@PathVariable Long id) {
        try {
            User user = userService.getUserByIdWithSessions(id);
            if (user == null) {
                log.error("User not found");
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(new ArrayList<>(user.getSessions().stream().
                    map(session -> new SessionResponse(user.getId(), session))
                    .collect(Collectors.toList())));
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/sessions/current")
    public ResponseEntity<SessionResponse> getCurrent(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authTokenHeader, @PathVariable Long id) {
        try {
            if (!authTokenHeader.startsWith("Bearer ")) {
                log.error("No token input");
                return ResponseEntity.badRequest().build();
            }
            String authToken = authTokenHeader.substring("Bearer ".length());

            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty()) {
                log.error("User not found");
                return ResponseEntity.notFound().build();
            }

            Session currentSession = user.get().getTokens().stream()
                    .filter(session -> session.getId().equals(UUID.fromString(authToken)))
                    .findFirst()
                    .get().getSession();

            return ResponseEntity.ok(new SessionResponse(currentSession));
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/sessions")
    public ResponseEntity<Session> newSession(@RequestBody LoginRequest request) {
        try {
            User user = userService.getUserByPhone(request.getPhone());
            if (user == null) {
                return ResponseEntity.status(404).build();
            }
            Session session = sessionService.create(user);
            Token token = tokenService.create(session);

            user.getSessions().add(session);
            user.getTokens().add(token);

            return ResponseEntity.ok(session);
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        try {
            tokenService.delete(id);
            Optional<Session> session = sessionService.getById(id);
            if (session.isEmpty()) {
                log.error("Session not found");
                return ResponseEntity.status(404).build();
            }

            sessionService.delete(id);
            User user = session.get().getUser();
            user.getSessions().remove(session);

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
