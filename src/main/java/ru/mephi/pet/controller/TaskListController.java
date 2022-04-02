package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TagDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.TaskListService;

@RestController
@RequestMapping("/api/taskList")
@RequiredArgsConstructor
public class TaskListController {
    private final TaskListService taskListService;

    @GetMapping("")
    public ResponseEntity<Iterable<TaskListDto>> getLists() {
        return ResponseEntity.ok(taskListService.getLists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskListDto> getList(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getList(id));
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<Iterable<TagDto>> getTags(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getTags(id));
    }

    @GetMapping("/{id}/records")
    public ResponseEntity<Iterable<RecordDto>> getRecords(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getRecords(id));
    }

    @PostMapping("/{id}/tag")
    public ResponseEntity<TagDto> addTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        return ResponseEntity.ok(taskListService.addTag(id, tagDto));
    }

    @PostMapping("/{id}/record")
    public ResponseEntity<RecordDto> addRecord(@PathVariable Long id, @RequestBody RecordDto recordDto) {
        return ResponseEntity.ok(taskListService.addRecord(id, recordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTaskList(@PathVariable Long id, @RequestBody TaskListDto taskListDto) {
        taskListService.updateTaskList(id, taskListDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/tag")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        taskListService.deleteTag(id, tagDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/record")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id, @RequestBody RecordDto recordDto) {
        taskListService.deleteRecord(id, recordDto);
        return ResponseEntity.noContent().build();
    }
}
