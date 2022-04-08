package ru.mephi.pet.domain;

import lombok.Data;
import ru.mephi.pet.enums.UserACL;

import java.io.Serializable;

@Data
public class UserGroupACLDto implements Serializable {
    private final UserDto user;
    private final UserACL userACL;
}
