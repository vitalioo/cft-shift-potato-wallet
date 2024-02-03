package ru.cft.template.api.model.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private Long phone;
    private String password;
}