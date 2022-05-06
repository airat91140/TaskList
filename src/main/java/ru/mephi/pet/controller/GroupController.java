package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.GroupDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.domain.UserDto;
import ru.mephi.pet.domain.UserGroupACLDto;
import ru.mephi.pet.enums.UserACL;
import ru.mephi.pet.service.GroupService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<GroupDto> getGroup(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroup(principal.getName(), id));
    }

    @GetMapping("/{id}/lists")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TaskListDto>> getGroupLists(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(groupService.getLists(principal.getName(), id));
    }

    @GetMapping("/{id}/users")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<UserGroupACLDto>> getUsers(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(groupService.getUsers(principal.getName(), id));
    }

    @DeleteMapping("/{id}/list")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteList(Principal principal, @PathVariable Long id, @RequestBody TaskListDto list) {
        groupService.deleteList(principal.getName(), id, list);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteUser{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteUser(Principal principal, @PathVariable Long id, @RequestBody UserDto user) {
        groupService.deleteUser(principal.getName(), id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<GroupDto> saveGroup(Principal principal, @RequestBody GroupDto group) {
        return ResponseEntity.ok(groupService.saveGroup(principal.getName(), group));
    }

    @PostMapping("/{id}/list")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TaskListDto> addList(Principal principal, @PathVariable Long id, @RequestBody TaskListDto list) {
        return ResponseEntity.ok(groupService.addList(principal.getName(), id, list));
    }

    @PostMapping("/{id}/user")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> addUser(Principal principal, @PathVariable Long id, @RequestBody UserDto user) {
        groupService.addUser(principal.getName(), id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updateGroup(Principal principal, @PathVariable Long id, @RequestBody GroupDto group) {
        groupService.updateGroup(principal.getName(), id, group);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ACL")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updateACL(Principal principal, @PathVariable Long id, @RequestBody UserDto userDto, @RequestBody UserACL userACL) {
        groupService.updateACL(principal.getName(), id, userDto, userACL);
        return ResponseEntity.noContent().build();
    }
}
