package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.enums.UserACL;
import ru.mephi.pet.exception.ForbiddenOperationException;
import ru.mephi.pet.exception.NotFoundException;
import ru.mephi.pet.repository.GroupRepository;
import ru.mephi.pet.repository.TaskListRepository;
import ru.mephi.pet.repository.UserGroupACLRepository;
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

    public UserDto getUser(String login) {
        return userMapper.toDto(userRepository.findByLogin(login).orElseThrow(NotFoundException::new));
    }

    public Iterable<TaskListDto> getUserLists(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(NotFoundException::new)
                .getTasks()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<GroupDto> getGroups(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(NotFoundException::new)
                .getGroups()
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toSet());
    }

    public TaskListDto addList(String login, TaskListDto list) {
        TaskList tasks = taskListRepository.save(taskListMapper.toEntity(list));
        User user = userRepository.findByLogin(login).orElseThrow();
        user.getTasks().add(tasks);
        userRepository.save(user);
        return taskListMapper.toDto(tasks);
    }

    public void deleteList(String login, TaskListDto list) {
        if (TaskListService.canWrite(taskListMapper.toEntity(list), userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            taskListRepository.deleteById(list.getId());
        else throw new ForbiddenOperationException();
    }

    public UserDto saveUser(UserSaveDto user) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public GroupDto addGroup(String login, GroupDto groupDto) {
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
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

    public void deleteUser(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        user.getGroups().forEach(g -> {
            g.getUsers().remove(user);
            g.getUserACLS().removeIf(a -> a.getUser().equals(user));
        });
        userRepository.delete(user);
    }

    public void updatePassword(String login, String password) {
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void updateUser(String login, UserDto userDto) {
        User u = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        u.setName(userDto.getName());
        u.setEmail(userDto.getEmail());
        u.setLogin(userDto.getLogin());
        userRepository.save(u);
    }
}
