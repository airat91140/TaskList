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

    @GetMapping("/getLists")
    public ResponseEntity<Iterable<TaskListDto>> getLists() {
        return ResponseEntity.ok(taskListService.getLists());
    }

    @GetMapping("/getList{id}")
    public ResponseEntity<TaskListDto> getList(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getList(id));
    }

    @GetMapping("/getTags{id}")
    public ResponseEntity<Iterable<TagDto>> getTags(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getTags(id));
    }

    @GetMapping("/getRecords{id}")
    public ResponseEntity<Iterable<RecordDto>> getRecords(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getRecords(id));
    }

    @PostMapping("/addTag{id}")
    public ResponseEntity<Void> addTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        taskListService.addTag(id, tagDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addRecord{id}")
    public ResponseEntity<Void> addRecord(@PathVariable Long id, @RequestBody RecordDto recordDto) {
        taskListService.addRecord(id, recordDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateHeader{id}")
    public ResponseEntity<Void> updateHeader(@PathVariable Long id, @RequestBody String header) {
        taskListService.updateHeader(id, header);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteTag{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        taskListService.deleteTag(id, tagDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteRecord{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id, @RequestBody RecordDto recordDto) {
        taskListService.deleteRecord(id, recordDto);
        return ResponseEntity.noContent().build();
    }
}
