package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.enums.UserACL;
import ru.mephi.pet.repository.GroupRepository;
import ru.mephi.pet.repository.TaskListRepository;
import ru.mephi.pet.repository.UserGroupACLRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TaskListRepository taskListRepository;
    private final UserGroupACLRepository userGroupACLRepository;
    private final UserMapper userMapper;
    private final TaskListMapper taskListMapper;
    private final GroupMapper groupMapper;

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

    public Iterable<GroupDto> getGroups(Long id) {
        return userRepository.findById(id)
                .orElseThrow()
                .getGroups()
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toSet());
    }

    public TaskListDto addList(Long id, TaskListDto list) {
        TaskList tasks = taskListRepository.save(taskListMapper.toEntity(list));
        User user = userRepository.findById(id).orElseThrow();
        user.getTasks().add(tasks);
        userRepository.save(user);
        return taskListMapper.toDto(tasks);
    }

    public void deleteList(Long id, TaskListDto list) {
        taskListRepository.deleteById(list.getId());
    }

    public UserDto saveUser(UserSaveDto user) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public GroupDto addGroup(Long id, GroupDto groupDto) {
        User user = userRepository.findById(id).orElseThrow();
        Group group = groupRepository.save(groupMapper.toEntity(groupDto));
        group.getUsers().add(user);
        user.getGroups().add(group);
        UserGroupACL userGroupACL = userGroupACLRepository.save(new UserGroupACL());
        userGroupACL.setUser(user);
        userGroupACL.setGroup(group);
        userGroupACL.setUserACL(UserACL.ADMIN);
        group.getUserACLS().add(userGroupACL);
        user.getGroupACLS().add(userGroupACL);
        userRepository.save(user);
        return groupMapper.toDto(group);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.getGroups().forEach(g -> {
            g.getUsers().remove(user);
            g.getUserACLS().removeIf(a -> a.getUser().equals(user));
        });
        userRepository.deleteById(id);
    }

    public void updatePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(password);
        userRepository.save(user);
    }

    public void updateUser(Long id, UserDto userDto) {
        User u = userRepository.findById(id).orElseThrow();
        u.setName(userDto.getName());
        u.setEmail(userDto.getEmail());
        u.setLogin(userDto.getLogin());
        userRepository.save(u);
    }
}
