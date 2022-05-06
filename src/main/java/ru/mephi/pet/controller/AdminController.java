package ru.mephi.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.service.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final GroupService groupService;
    private final RecordService recordService;
    private final TagService tagService;
    private final TaskListService taskListService;

    @GetMapping("/user")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/user/{login}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<UserDto> getUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUser(login));
    }

    @GetMapping("/group")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<GroupDto>> getGroup() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @GetMapping("/group/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @GetMapping("/user/{login}/lists")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TaskListDto>> getUserLists(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserLists(login));
    }

    @GetMapping("/user/{login}/groups")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<GroupDto>> getGroups(@PathVariable String login) {
        return ResponseEntity.ok(userService.getGroups(login));
    }

    @GetMapping("group/{id}/lists")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TaskListDto>> getGroupLists(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getLists(id));
    }

    @GetMapping("group/{id}/users")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<UserGroupACLDto>> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getUsers(id));
    }

    @DeleteMapping("/user/{login}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/group/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserSaveDto user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/taskList")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TaskListDto>> getLists() {
        return ResponseEntity.ok(taskListService.getLists());
    }

    @GetMapping("/taskList/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<TaskListDto> getList(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getList(id));
    }

    @GetMapping("/taskList/{id}/tags")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TagDto>> getTags(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getTags(id));
    }

    @GetMapping("/taskList/{id}/records")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<RecordDto>> getRecords(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getRecords(id));
    }

    @DeleteMapping("/taskList/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        taskListService.deleteList(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/record")
    @RolesAllowed("ROLE_ADMIN")
    ResponseEntity<Iterable<RecordDto>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }

    @GetMapping("/record/{id}")
    @RolesAllowed("ROLE_ADMIN")
    ResponseEntity<RecordDto> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @GetMapping("/record/{id}/list")
    @RolesAllowed("ROLE_ADMIN")
    ResponseEntity<TaskListDto> getRecordParentList(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getList(id));
    }

    @GetMapping("/tag")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TagDto>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping("/tag/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<TagDto> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @GetMapping("/tag/{id}/lists")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Iterable<TaskListDto>> getLists(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getLists(id));
    }
}
