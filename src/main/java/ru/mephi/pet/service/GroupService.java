package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.repository.GroupRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
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
    
    public void updateName(Long id, String name) {
        groupRepository.findById(id).orElseThrow().setName(name);
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
    public void addUser(Long id, UserDto user) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().add(userMapper.toEntity(user));
        userRepository.findById(user.getId()).orElseThrow().getGroups().add(group);
    }
    
    public void addList(Long id, TaskListDto list) {
        groupRepository.findById(id).orElseThrow().getTasks().add(taskListMapper.toEntity(list));
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteList(Long id, TaskListDto list) { // ToDo как удалить из базы данных список
        groupRepository.findById(id).orElseThrow().getTasks().remove(taskListMapper.toEntity(list));
        throw new NotImplementedException();
    }

    public void deleteUser(Long id, User userDto) { // ToDo как реализовать удаление группы из множества у пользователя?
        Group group = groupRepository.findById(id).orElseThrow();
        //user.getGroups().remove(group);
        throw new NotImplementedException();
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        group.getUsers().forEach(u -> u.getGroups().remove(group));
        groupRepository.delete(group);
    }

    public void deleteAllGroups() {
        groupRepository.deleteAll();
    }
    
}
