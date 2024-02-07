package ru.cft.template.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Session {
    private static final int THIRTY_MINUTES_IN_MILLISECONDS = 30 * 60 * 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date expirationTime;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnore
    private User user;

    public void setExpirationTimeIn30Minutes() {
        this.expirationTime = new Date(System.currentTimeMillis() + THIRTY_MINUTES_IN_MILLISECONDS);
        this.active = true;
    }
}
