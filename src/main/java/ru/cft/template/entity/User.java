package ru.cft.template.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.cft.template.api.model.Session;
import ru.cft.template.api.model.Token;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "persons")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id")
    private String walletID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private Long phone;

    @Column(name = "registration_date")
    private Date registrationDate = new Date();

    @Column(name = "last_update_date")
    private Date lastUpdateDate = new Date();
    private Integer age;
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Session> sessions;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Token> tokens;
}
