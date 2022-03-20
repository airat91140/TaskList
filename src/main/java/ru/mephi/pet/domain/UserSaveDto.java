package ru.mephi.pet.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSaveDto implements Serializable {
    private final Long id;
    private final String login;
    private final String password;
    private final String email;
    private final String name;
}
