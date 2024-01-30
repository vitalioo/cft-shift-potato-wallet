package ru.cft.template.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "persons")
public class User {
    @Id
    private UUID id;
    private String walletID;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Long phone;
    private final Date registrationDate = new Date();
    private final Date lastUpdateDate = new Date();
    private Integer age;
    private String password;
}
