package ru.cft.template.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.cft.template.entity.User;

import java.util.Date;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date expirationTime;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public void setExpirationTimeIn30Minutes() {
        final int THIRTY_MINUTES_IN_MILLISECONDS = 30 * 60 * 1000;
        this.expirationTime = new Date(System.currentTimeMillis() + THIRTY_MINUTES_IN_MILLISECONDS);
        this.active = true;
    }
}
