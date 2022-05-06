package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }

    @GetMapping("/lists")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<TaskListDto>> getUserLists(Principal principal) {
        return ResponseEntity.ok(userService.getUserLists(principal.getName()));
    }

    @GetMapping("/groups")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Iterable<GroupDto>> getGroups(Principal principal) {
        return ResponseEntity.ok(userService.getGroups(principal.getName()));
    }


    @DeleteMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteList(Principal principal, @RequestBody TaskListDto list) {
        userService.deleteList(principal.getName(), list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TaskListDto> addList(Principal principal, @RequestBody TaskListDto list) {
        return ResponseEntity.ok(userService.addList(principal.getName(), list));
    }

    @PostMapping("/group")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<GroupDto> addGroup(Principal principal, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(userService.addGroup(principal.getName(), groupDto));
    }

    @PutMapping("/password")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updatePassword(Principal principal, @RequestBody String password) {
        userService.updatePassword(principal.getName(), password);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> updateUser(Principal principal, @RequestBody UserDto user) {
        userService.updateUser(principal.getName(), user);
        return ResponseEntity.noContent().build();
    }
}
