package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.RecordService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/api/record")
@RestController
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<RecordDto> getRecord(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecord(principal.getName(), id));
    }

    @GetMapping("/{id}/list")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<TaskListDto> getList(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(recordService.getList(principal.getName(), id));
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Void> updateDeadline(Principal principal, @PathVariable Long id, @RequestBody RecordDto recordDto) {
        recordService.updateRecord(principal.getName(), id, recordDto);
        return ResponseEntity.noContent().build();
    }
}
