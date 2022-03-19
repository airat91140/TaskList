package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.TaskListService;

@RestController
@RequestMapping("/api/taskList")
@RequiredArgsConstructor
public class TaskListController {
    private final TaskListService taskListService;

    @GetMapping("/getLists")
    public ResponseEntity<Iterable<TaskListDto>> getLists() {
        return ResponseEntity.ok(taskListService.getLists());
    }
}
