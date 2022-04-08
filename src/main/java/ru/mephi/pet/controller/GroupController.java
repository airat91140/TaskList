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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("")
    public ResponseEntity<Iterable<GroupDto>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @GetMapping("/{id}/lists")
    public ResponseEntity<Iterable<TaskListDto>> getGroupLists(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getLists(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Iterable<UserGroupACLDto>> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getUsers(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/list")
    public ResponseEntity<Void> deleteList(@PathVariable Long id, @RequestBody TaskListDto list) {
        groupService.deleteList(id, list);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteUser{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserDto user) {
        groupService.deleteUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto group) {
        return ResponseEntity.ok(groupService.saveGroup(group));
    }

    @PostMapping("/{id}/list")
    public ResponseEntity<TaskListDto> addList(@PathVariable Long id, @RequestBody TaskListDto list) {
        return ResponseEntity.ok(groupService.addList(id, list));
    }

    @PostMapping("/{id}/user")
    public ResponseEntity<Void> addUser(@PathVariable Long id, @RequestBody UserDto user) {
        groupService.addUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long id, @RequestBody GroupDto group) {
        groupService.updateGroup(id, group);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ACL")
    public ResponseEntity<Void> updateACL(@PathVariable Long id, @RequestBody UserDto userDto, @RequestBody UserACL userACL) {
        groupService.updateACL(id, userDto, userACL);
        return ResponseEntity.noContent().build();
    }
}
