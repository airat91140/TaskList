package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.TagDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.service.TagService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TagDto>> getTags(Principal principal) {
        return ResponseEntity.ok(tagService.getTags(principal.getName()));
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TagDto> getTag(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(principal.getName(), id));
    }

    @GetMapping("/{id}/lists")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TaskListDto>> getLists(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(tagService.getLists(principal.getName(), id));
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updateTag(Principal principal, @PathVariable Long id, @RequestBody TagDto tagDto) {
        tagService.updateTag(principal.getName(), id, tagDto);
        return ResponseEntity.noContent().build();
    }
}
