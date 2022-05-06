package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.enums.UserACL;
import ru.mephi.pet.exception.ForbiddenOperationException;
import ru.mephi.pet.exception.NoACLException;
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
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TaskListRepository taskListRepository;
    private final UserGroupACLRepository userGroupACLRepository;
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;
    private final TaskListMapper taskListMapper;

    private boolean groupHas(Group group, String login) {
        if (group.getUsers() == null)
            group = groupRepository.findById(group.getId()).orElseThrow(NotFoundException::new);
        return group.getUsers().stream().anyMatch(u -> u.getLogin().equals(login));
    }

    public Iterable<GroupDto> getGroups() {
        Iterable<Group> list = groupRepository.findAll();
        List<GroupDto> result = new LinkedList<>();
        list.forEach(g -> result.add(groupMapper.toDto(g)));
        return result;
    }

    public GroupDto getGroup(Long id) {
        return groupMapper.toDto(groupRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public GroupDto getGroup(String login, Long id) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (groupHas(group, login))
            throw new ForbiddenOperationException();
        return groupMapper.toDto(group);
    }

    public Iterable<UserGroupACLDto> getUsers(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(NotFoundException::new)
                .getUserACLS()
                .stream()
                .map(userMapper::userGroupACLToUserGroupACLDto)
                .collect(Collectors.toSet());
    }

    public Iterable<UserGroupACLDto> getUsers(String login, Long id) {
        Group group =  groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!groupHas(group, login))
            throw new ForbiddenOperationException();
        return group.getUserACLS()
                .stream()
                .map(userMapper::userGroupACLToUserGroupACLDto)
                .collect(Collectors.toSet());
    }

    public Iterable<TaskListDto> getLists(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(NotFoundException::new)
                .getTasks()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<TaskListDto> getLists(String login, Long id) {
        Group group =  groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!groupHas(group, login))
            throw new ForbiddenOperationException();
        return group.getTasks()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toSet());
    }

    public void updateGroup(String login, Long id, GroupDto groupDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        if (!findACL(group, login).getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        group.setName(groupDto.getName());
        groupRepository.save(group);
    }

    private UserGroupACL findACL(Group group, String login) {
        return group.getUserACLS()
                .stream()
                .filter(uACL -> uACL.getUser().getLogin().equals(login))
                .findAny().orElseThrow(NotFoundException::new);
    }

    public void updateACL(String login, Long id, UserDto userDto, UserACL acl) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        User user = userMapper.toEntity(userDto);
        if (!findACL(group, login).getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        UserGroupACL participant = findACL(group, user.getLogin());
        if (participant.getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        participant.setUserACL(acl);
        userGroupACLRepository.save(participant);
    }

    public void addUser(String login, Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!findACL(group, login).getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        User user = userRepository.findById(userDto.getId()).orElseThrow(NotFoundException::new);
        group.getUsers().add(user);
        user.getGroups().add(group);
        UserGroupACL userGroupACL = userGroupACLRepository.save(new UserGroupACL());
        userGroupACL.setGroup(group);
        userGroupACL.setUser(user);
        userGroupACL.setUserACL(UserACL.READER);
        group.getUserACLS().add(userGroupACL);
        user.getGroupACLS().add(userGroupACL);
        groupRepository.save(group);
    }

    public TaskListDto addList(String login, Long id, TaskListDto list) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (findACL(group, login).getUserACL().equals(UserACL.READER))
            throw new ForbiddenOperationException();
        TaskList tasks = taskListMapper.toEntity(list);
        tasks.setOwner(group);
        group.getTasks().add(tasks);
        groupRepository.save(group);
        return taskListMapper.toDto(tasks);
    }

    public GroupDto saveGroup(String login, GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        group.getUsers().add(user);
        user.getGroups().add(group);
        UserGroupACL userGroupACL = userGroupACLRepository.save(new UserGroupACL());
        userGroupACL.setGroup(group);
        userGroupACL.setUser(user);
        userGroupACL.setUserACL(UserACL.ADMIN);
        group.getUserACLS().add(userGroupACL);
        user.getGroupACLS().add(userGroupACL);
        return groupMapper.toDto(groupRepository.save(group));
    }

    public void deleteList(String login, Long id, TaskListDto list) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!findACL(group, login).getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        taskListRepository.deleteById(list.getId());
    }

    public void deleteUser(String login, Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!findACL(group, login).getUserACL().equals(UserACL.ADMIN))
            throw new ForbiddenOperationException();
        User user = userRepository.findById(userDto.getId()).orElseThrow(NotFoundException::new);
        user.getGroups().remove(group);
        group.getUsers().remove(user);
        if (!group.getUserACLS().isEmpty() && group.getUserACLS().stream().noneMatch(g -> g.getUserACL().equals(UserACL.ADMIN)))
            group.getUserACLS().stream().findAny().orElseThrow().setUserACL(UserACL.ADMIN);
        groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().forEach(u -> {
            u.getGroups().remove(group);
            u.getGroupACLS().removeIf(a -> a.getGroup().equals(group));
        });
        groupRepository.delete(group);
    }
}
