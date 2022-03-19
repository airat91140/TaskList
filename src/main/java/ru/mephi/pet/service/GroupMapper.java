package ru.mephi.pet.service;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import ru.mephi.pet.domain.Group;
import ru.mephi.pet.domain.GroupDto;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);
    Group toEntity(GroupDto groupDto);
}
