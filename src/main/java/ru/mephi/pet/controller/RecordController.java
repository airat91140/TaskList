package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.RecordService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/record")
@RestController
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/getRecords")
    ResponseEntity<Iterable<RecordDto>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }

    @GetMapping("/getRecord{id}")
    ResponseEntity<RecordDto> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @GetMapping("/getList{id}")
    ResponseEntity<TaskListDto> getList(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getList(id));
    }

    @PutMapping("/updateDeadline{id}")
    ResponseEntity<Void> updateDeadline(@PathVariable Long id, @RequestBody LocalDateTime deadline) {
        recordService.updateDeadline(id, deadline);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateData{id}")
    ResponseEntity<Void> updateData(@PathVariable Long id, @RequestBody String data) {
        recordService.updateData(id, data);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateIsDone{id}")
    ResponseEntity<Void> updateIsDone(@PathVariable Long id, @RequestBody Boolean isDone) {
        recordService.updateIsDone(id, isDone);
        return ResponseEntity.noContent().build();
    }
}
