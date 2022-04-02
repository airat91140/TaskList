package ru.mephi.pet.service;

import org.mapstruct.Mapper;
import ru.mephi.pet.domain.TaskList;
import ru.mephi.pet.domain.TaskListDto;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    TaskListDto toDto(TaskList taskList);

    TaskList toEntity(TaskListDto taskListDto);
}
