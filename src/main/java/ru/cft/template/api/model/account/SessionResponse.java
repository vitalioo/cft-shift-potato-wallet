package ru.cft.template.api.model.account;

import lombok.Getter;
import lombok.Setter;
import ru.cft.template.api.model.Session;

import java.util.Date;

@Setter
@Getter
public class SessionResponse {
    private Long id;
    private Long userId;
    private Date expirationTime;
    private boolean active;

    public SessionResponse(Long userId, Session session) {
        this.userId = userId;
        this.id = session.getId();
        this.expirationTime = session.getExpirationTime();
        this.active = session.isActive();
    }

    public SessionResponse(Session session) {
        this.id = session.getId();
        this.userId = session.getUser().getId();
        this.expirationTime = session.getExpirationTime();
        this.active = session.isActive();
    }
}
