package ru.cft.template.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users/sessions")
@Slf4j
public class SessionController {
    private final UserService userService;
    private final SessionService sessionService;
    private final TokenService tokenService;

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
