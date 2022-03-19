package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<Iterable<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/getUsers{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/getLists{id}")
    public ResponseEntity<Iterable<TaskListDto>> getUserLists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserLists(id));
    }

    @GetMapping("/getGroups{id}")
    public ResponseEntity<Iterable<Group>> getGroups(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getGroups(id));
    }

    @DeleteMapping("/deleteUsers{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteList{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id, @RequestBody TaskListDto list){
        userService.deleteList(id, list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/saveUser")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/addList{id}")
    public ResponseEntity<Void> addList(@PathVariable Long id, @RequestBody TaskListDto list) {
        userService.addList(id, list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody String login, @RequestBody String password) {
        return ResponseEntity.ok(userService.login(login, password));
    }

    @PutMapping("/updatePassword{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateName{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String name) {
        userService.updateName(id, name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateEmail{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.noContent().build();
    }
}
