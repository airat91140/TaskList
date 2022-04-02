package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.RecordService;

@RequiredArgsConstructor
@RequestMapping("/api/record")
@RestController
public class RecordController {
    private final RecordService recordService;

    @GetMapping("")
    ResponseEntity<Iterable<RecordDto>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }

    @GetMapping("/{id}")
    ResponseEntity<RecordDto> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @GetMapping("/{id}/list")
    ResponseEntity<TaskListDto> getList(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getList(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateDeadline(@PathVariable Long id, @RequestBody RecordDto recordDto) {
        recordService.updateRecord(id, recordDto);
        return ResponseEntity.noContent().build();
    }
}
