package ru.mephi.pet.service;

import org.mapstruct.Mapper;
import ru.mephi.pet.domain.Record;
import ru.mephi.pet.domain.RecordDto;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordDto toDto(Record record);

    Record toEntity(RecordDto recordDto);
}
