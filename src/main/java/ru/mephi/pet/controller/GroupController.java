package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.service.GroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/getGroups")
    public ResponseEntity<Iterable<GroupDto>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @GetMapping("/getGroup{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @GetMapping("/getLists{id}")
    public ResponseEntity<Iterable<TaskListDto>> getGroupLists(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getLists(id));
    }

    @GetMapping("/getUsers{id}")
    public ResponseEntity<Iterable<UserDto>> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getUsers(id));
    }

    @DeleteMapping("/deleteGroups{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteGroups")
    public ResponseEntity<Void> deleteAllGroups() {
        groupService.deleteAllGroups();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteList{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id, @RequestBody TaskListDto list){
        groupService.deleteList(id, list);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteUser{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserDto user) {
        groupService.deleteUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/saveGroup")
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto group) {
        return ResponseEntity.ok(groupService.saveGroup(group));
    }

    @PostMapping("/addList{id}")
    public ResponseEntity<Void> addList(@PathVariable Long id, @RequestBody TaskListDto list) {
        groupService.addList(id, list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addUser{id}")
    public ResponseEntity<Void> addUser(@PathVariable Long id, @RequestBody UserDto user) {
        groupService.addUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateName{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String name) {
        groupService.updateName(id, name);
        return ResponseEntity.noContent().build();
    }
}
