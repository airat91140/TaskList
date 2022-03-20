package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.TagDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.TagService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("/getTags")
    public ResponseEntity<Iterable<TagDto>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping("/getTag{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @GetMapping("/getLists{id}")
    public ResponseEntity<Iterable<TaskListDto>> getLists(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getLists(id));
    }

    @PutMapping("/updateData{id}")
    public ResponseEntity<Void> updateData(@PathVariable Long id, @RequestBody String data) {
        tagService.updateData(id, data);
        return ResponseEntity.noContent().build();
    }
}
