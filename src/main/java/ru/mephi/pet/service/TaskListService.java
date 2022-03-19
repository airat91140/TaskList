package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.TaskList;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.repository.TaskListRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final TaskListMapper taskListMapper;

    public Iterable<TaskListDto> getLists() {
        Iterable<TaskList> list = taskListRepository.findAll();
        List<TaskListDto> result = new LinkedList<>();
        list.forEach(t -> result.add(taskListMapper.toDto(t)));
        return result;
    }
}
