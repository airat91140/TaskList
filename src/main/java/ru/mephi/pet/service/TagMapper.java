package ru.mephi.pet.service;

import org.mapstruct.Mapper;
import ru.mephi.pet.domain.Tag;
import ru.mephi.pet.domain.TagDto;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);
}
