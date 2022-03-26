package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
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
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TaskListRepository taskListRepository;
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

    public Iterable<UserDto> getUsers(Long id) {
        return groupRepository.findById(id)
                .orElseThrow()
                .getUsers()
                .stream()
                .map(userMapper::toDto)
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

    /*
    public void updateRole(Long id, User user, GroupRole role) {
        Group group = groupRepository.findById(id).orElseThrow();
        GroupParticipant participant = group
                .getUsers()
                .stream()
                .filter(u -> u.getUser().getId().equals(user.getId()))
                .peek(u -> u.setGroupRole(role))
                .findAny()
                .orElseThrow();
        if (role.equals(GroupRole.ADMIN)) {
            group.getAdmin().setGroupRole(GroupRole.WRITER);
            group.setAdmin(participant);
        }
    }
*/
    public void addUser(Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().add(userMapper.toEntity(userDto));
        groupRepository.save(group);
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        user.getGroups().add(group);
        userRepository.save(user);
    }

    public TaskListDto addList(Long id, TaskListDto list) {
        TaskList tasks = taskListRepository.save(taskListMapper.toEntity(list));
        Group group = groupRepository.findById(id).orElseThrow();
        group.getTasks().add(tasks);
        groupRepository.save(group);
        return taskListMapper.toDto(tasks);
    }

    public GroupDto saveGroup(GroupDto group) {
        return groupMapper.toDto(groupRepository.save(groupMapper.toEntity(group)));
    }

    public void deleteList(Long id, TaskListDto list) {
        groupRepository.findById(id).orElseThrow().getTasks().remove(taskListMapper.toEntity(list));
        taskListRepository.deleteById(list.getId());
    }

    public void deleteUser(Long id, UserDto userDto) {
        Group group = groupRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        user.getGroups().remove(group);
        group.getUsers().remove(user);
        userRepository.save(user);
        groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        List<User> toSave = new LinkedList<>();
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().forEach(u -> {
            u.getGroups().remove(group);
            toSave.add(u);
        });
        userRepository.saveAll(toSave);
        groupRepository.delete(group);
    }

    public void deleteAllGroups() {
        groupRepository.deleteAll();
    }

}
