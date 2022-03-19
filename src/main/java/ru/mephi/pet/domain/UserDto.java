package ru.mephi.pet.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {
    private final Long id;
    private final String login;
    private final String name;
}
