package ru.mephi.pet.service;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.mephi.pet.domain.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User toEntity(UserSaveDto userSaveDto);

    UserGroupACL userGroupACLDtoToUserGroupACL(UserGroupACLDto userGroupACLDto);

    UserGroupACLDto userGroupACLToUserGroupACLDto(UserGroupACL userGroupACL);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserGroupACLFromUserGroupACLDto(UserGroupACLDto userGroupACLDto, @MappingTarget UserGroupACL userGroupACL);
}
