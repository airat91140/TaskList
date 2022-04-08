package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.enums.UserACL;
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

    public Iterable<GroupDto> getGroups() {
        Iterable<Group> list = groupRepository.findAll();
        List<GroupDto> result = new LinkedList<>();
        list.forEach(g -> result.add(groupMapper.toDto(g)));
        return result;
    }

    public GroupDto getGroup(Long id) {
        return groupMapper.toDto(groupRepository.findById(id).orElseThrow());
    }

    public Iterable<UserGroupACLDto> getUsers(Long id) {
        return groupRepository.findById(id)
                .orElseThrow()
                .getUserACLS()
                .stream()
                .map(userMapper::userGroupACLToUserGroupACLDto)
                .collect(Collectors.toSet());
    }

    public Iterable<TaskListDto> getLists(Long id) {
        return groupRepository.findById(id)
                .orElseThrow()
                .getTasks()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toSet());
    }

    public void updateGroup(Long id, GroupDto groupDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.setName(groupDto.getName());
        groupRepository.save(group);
    }

    public void updateACL(Long id, UserDto userDto, UserACL acl) {
        Group group = groupRepository.findById(id).orElseThrow();
        User user = userMapper.toEntity(userDto);
        UserGroupACL participant = group
                .getUserACLS()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .findAny()
                .orElseThrow();
        if (participant.getUserACL().equals(UserACL.ADMIN))
            throw new RuntimeException("");
        participant.setUserACL(acl);
    }

    public void addUser(Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().add(userMapper.toEntity(userDto));
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        user.getGroups().add(group);
        UserGroupACL userGroupACL = userGroupACLRepository.save(new UserGroupACL());
        userGroupACL.setGroup(group);
        userGroupACL.setUser(user);
        userGroupACL.setUserACL(UserACL.READER);
        group.getUserACLS().add(userGroupACL);
        user.getGroupACLS().add(userGroupACL);
        groupRepository.save(group);
    }

    public TaskListDto addList(Long id, TaskListDto list) {
        Group group = groupRepository.findById(id).orElseThrow();
        TaskList tasks = taskListMapper.toEntity(list);
        tasks.setOwner(group);
        group.getTasks().add(tasks);
        groupRepository.save(group);
        return taskListMapper.toDto(tasks);
    }

    public GroupDto saveGroup(GroupDto group) {
        return groupMapper.toDto(groupRepository.save(groupMapper.toEntity(group)));
    }

    public void deleteList(Long id, TaskListDto list) {
        taskListRepository.deleteById(list.getId());
    }

    public void deleteUser(Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userDto.getId()).orElseThrow();
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
