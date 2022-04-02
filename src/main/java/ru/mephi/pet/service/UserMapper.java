package ru.mephi.pet.service;

import org.mapstruct.Mapper;
import ru.mephi.pet.domain.User;
import ru.mephi.pet.domain.UserDto;
import ru.mephi.pet.domain.UserSaveDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User toEntity(UserSaveDto userSaveDto);
}
