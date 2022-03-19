package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.repository.GroupRepository;
import ru.mephi.pet.repository.TaskListRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TaskListRepository taskListRepository;
    private final UserMapper userMapper;
    private final TaskListMapper taskListMapper;

    public Iterable<UserDto> getUsers() {
        Iterable<User> list = userRepository.findAll();
        List<UserDto> result = new LinkedList<>();
        list.forEach(user -> result.add(userMapper.toDto(user)));
        return result;
    }

    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow());
    }

    public Iterable<TaskListDto> getUserLists(Long id) {
        return userRepository.findById(id)
                .orElseThrow()
                .getTasks()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<Group> getGroups(Long id) {
        return userRepository.findById(id).orElseThrow().getGroups();
    }

    public void addList(Long id, TaskListDto list) {
        userRepository.findById(id).orElseThrow().getTasks().add(taskListMapper.toEntity(list));
    }

    public void deleteList(Long id, TaskListDto list) {
        userRepository.findById(id).orElseThrow().getTasks().remove(taskListMapper.toEntity(list));
        taskListRepository.deleteById(list.getId());
    }

    public UserDto saveUser (UserDto user) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void updatePassword(Long id, String password) {
        userRepository.findById(id).orElseThrow().setPassword(password);
    }

    public void updateEmail(Long id, String email) {
        userRepository.findById(id).orElseThrow().setEmail(email);
    }

    public void updateName(Long id, String name) {
        userRepository.findById(id).orElseThrow().setName(name);
    }
}
