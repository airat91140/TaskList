package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{id}/lists")
    public ResponseEntity<Iterable<TaskListDto>> getUserLists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserLists(id));
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Iterable<GroupDto>> getGroups(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getGroups(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/list")
    public ResponseEntity<Void> deleteList(@PathVariable Long id, @RequestBody TaskListDto list) {
        userService.deleteList(id, list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserSaveDto user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/{id}/list")
    public ResponseEntity<TaskListDto> addList(@PathVariable Long id, @RequestBody TaskListDto list) {
        return ResponseEntity.ok(userService.addList(id, list));
    }

    @PostMapping("/{id}/group")
    public ResponseEntity<GroupDto> addGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(userService.addGroup(id, groupDto));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
        userService.updateUser(id, user);
        return ResponseEntity.noContent().build();
    }
}
