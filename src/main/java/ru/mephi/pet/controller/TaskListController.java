package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TagDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.TaskListService;
import ru.mephi.pet.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping("/api/taskList")
@RequiredArgsConstructor
public class TaskListController {
    private final TaskListService taskListService;
    private final UserService userService;

    @GetMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TaskListDto>> getLists(Principal principal) {
        return ResponseEntity.ok(userService.getUserLists(principal.getName()));
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TaskListDto> getList(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getList(principal.getName(), id));
    }

    @GetMapping("/{id}/tags")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TagDto>> getTags(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getTags(principal.getName(), id));
    }

    @GetMapping("/{id}/records")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<RecordDto>> getRecords(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getRecords(principal.getName(), id));
    }

    @PostMapping("/{id}/tag")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TagDto> addTag(Principal principal, @PathVariable Long id, @RequestBody TagDto tagDto) {
        return ResponseEntity.ok(taskListService.addTag(principal.getName(), id, tagDto));
    }

    @PostMapping("/{id}/record")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<RecordDto> addRecord(Principal principal, @PathVariable Long id, @RequestBody RecordDto recordDto) {
        return ResponseEntity.ok(taskListService.addRecord(principal.getName(), id, recordDto));
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updateTaskList(Principal principal, @PathVariable Long id, @RequestBody TaskListDto taskListDto) {
        taskListService.updateTaskList(principal.getName(), id, taskListDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/tag")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteTag(Principal principal, @PathVariable Long id, @RequestBody TagDto tagDto) {
        taskListService.deleteTag(principal.getName(), id, tagDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/record")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteRecord(Principal principal, @PathVariable Long id, @RequestBody RecordDto recordDto) {
        taskListService.deleteRecord(principal.getName(), id, recordDto);
        return ResponseEntity.noContent().build();
    }
}
