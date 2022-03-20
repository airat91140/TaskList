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

    public String getEmail(Long id) {
        return userRepository.findById(id).orElseThrow().getEmail();
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
        TaskList tasks = taskListRepository.save(taskListMapper.toEntity(list));
        userRepository.findById(id).orElseThrow().getTasks().add(tasks);
    }

    public void deleteList(Long id, TaskListDto list) {
        userRepository.findById(id).orElseThrow().getTasks().remove(taskListMapper.toEntity(list));
        taskListRepository.deleteById(list.getId());
    }

    public UserDto saveUser (UserSaveDto user) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void updatePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(password);
        userRepository.save(user);
    }

    public void updateEmail(Long id, String email) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(email);
        userRepository.save(user);
    }

    public void updateName(Long id, String name) {
        var u = userRepository.findById(id).orElseThrow();
        u.setName(name);
        userRepository.save(u);
    }
}
