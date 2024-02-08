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
        Optional<User> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Registration failed");
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionResponse>> getSessions(@PathVariable Long id) {
        User user = userService.getUserByIdWithSessions(id);

        return ResponseEntity.ok(new ArrayList<>(user.getSessions().stream().
                map(session -> new SessionResponse(user.getId(), session))
                .toList()));
    }

    @GetMapping("/{id}/sessions/current")
    public ResponseEntity<SessionResponse> getCurrent(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authTokenHeader, @PathVariable Long id) {
        return ResponseEntity.ok(new SessionResponse(sessionService.getByUser(userService.getUserById(id), authTokenHeader)));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionResponse> newSession(@RequestBody LoginRequest request) {
        User user = userService.getUserByPhone(request.getPhone());
        Session session = sessionService.create(user);
        Token token = tokenService.create(session);

        userService.saveSessionAndToken(user, session, token);

        return ResponseEntity.ok(new SessionResponse(session));
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        tokenService.delete(id);
        Optional<Session> session = sessionService.getById(id);

        sessionService.delete(id);
        userService.deleteSession(session.get().getUser(), session);

        return ResponseEntity.ok().build();

    }
}
