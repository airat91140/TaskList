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

    @GetMapping("")
    public ResponseEntity<Iterable<TagDto>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @GetMapping("/{id}/lists")
    public ResponseEntity<Iterable<TaskListDto>> getLists(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getLists(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        tagService.updateTag(id, tagDto);
        return ResponseEntity.noContent().build();
    }
}
