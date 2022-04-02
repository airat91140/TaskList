package ru.mephi.pet.service;

import org.mapstruct.Mapper;
import ru.mephi.pet.domain.Group;
import ru.mephi.pet.domain.GroupDto;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);

    Group toEntity(GroupDto groupDto);
}
