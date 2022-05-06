package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.enums.UserACL;
import ru.mephi.pet.exception.ForbiddenOperationException;
import ru.mephi.pet.exception.NotFoundException;
import ru.mephi.pet.exception.NotOwnedByGroupException;
import ru.mephi.pet.repository.RecordRepository;
import ru.mephi.pet.repository.TagRepository;
import ru.mephi.pet.repository.TaskListRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final TagRepository tagRepository;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final TaskListMapper taskListMapper;
    private final TagMapper tagMapper;
    private final RecordMapper recordMapper;

    public Iterable<TaskListDto> getLists() {
        Iterable<TaskList> list = taskListRepository.findAll();
        List<TaskListDto> result = new LinkedList<>();
        list.forEach(t -> result.add(taskListMapper.toDto(t)));
        return result;
    }

    public Iterable<TaskListDto> getLists(String login) {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new)
                .getTasks().stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskListDto getList(Long id) {
        return taskListMapper.toDto(taskListRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public TaskListDto getList(String login, Long id) {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new)
                .getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .map(taskListMapper::toDto)
                .orElseThrow(ForbiddenOperationException::new);
    }

    public Iterable<TagDto> getTags(Long id) {
        return taskListRepository.findById(id).orElseThrow(NotFoundException::new)
                .getTags().stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<TagDto> getTags(String login, Long id) {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new)
                .getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .orElseThrow(ForbiddenOperationException::new)
                .getTags()
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<RecordDto> getRecords(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow(NotFoundException::new)
                .getRecords()
                .stream()
                .map(recordMapper::toDto)
                .collect(Collectors.toList());
    }

    public Iterable<RecordDto> getRecords(String login, Long id) {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new)
                .getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .orElseThrow(ForbiddenOperationException::new)
                .getRecords()
                .stream()
                .map(recordMapper::toDto)
                .collect(Collectors.toSet());
    }

    public TagDto addTag(String login, Long id, TagDto tagDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!canWrite(list, userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        Tag tag = tagRepository.findById(tagDto.getId()).orElse(tagRepository.save(tagMapper.toEntity(tagDto)));
        list.getTags().add(tag);
        if (tag.getLists() == null)
            tag.setLists(new LinkedHashSet<>());
        tag.getLists().add(list);
        taskListRepository.save(list);
        return tagMapper.toDto(tag);
    }

    public RecordDto addRecord(String login, Long id, RecordDto recordDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!canWrite(list, userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        Record record = recordRepository.save(recordMapper.toEntity(recordDto));
        list.getRecords().add(record);
        record.setParentList(list);
        taskListRepository.save(list);
        return recordMapper.toDto(record);
    }

    public void updateTaskList(String login, Long id, TaskListDto taskListDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!isAdmin(list, userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        list.setHeader(taskListDto.getHeader());
        taskListRepository.save(list);
    }

    public void deleteTag(String login, Long id, TagDto tagDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!canWrite(list, userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        list.getTags().remove(tagMapper.toEntity(tagDto));
        tagRepository.findById(tagDto.getId()).orElseThrow(NotFoundException::new).getLists().remove(list);
        taskListRepository.save(list);
    }

    public void deleteRecord(String login, Long id, RecordDto recordDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!canWrite(list, userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        list.getRecords().remove(recordMapper.toEntity(recordDto));
        recordRepository.deleteById(recordDto.getId());
        taskListRepository.save(list);
    }

    public void deleteList(Long id) {
        taskListRepository.deleteById(id);
    }

    public static boolean canRead(TaskList taskList, User user) {
        if (taskList.getOwner() == null)
            if (user.getTasks().contains(taskList))
                return true;
        else
            return taskList.getOwner().getUserACLS().stream().anyMatch(a -> a.getUser().equals(user));
        return false;
    }

    public static boolean canWrite(TaskList taskList, User user) {
        if (taskList.getOwner() == null)
            return user.getTasks().contains(taskList);
        else
            return taskList.getOwner().getUserACLS().stream().anyMatch(a -> a.getUser().equals(user) && !a.getUserACL().equals(UserACL.READER));
    }

    public static boolean isAdmin(TaskList taskList, User user) {
        if (taskList.getOwner() == null)
            throw new NotOwnedByGroupException();
        return taskList.getOwner().getUserACLS().stream().anyMatch(a -> a.getUser().equals(user) && a.getUserACL().equals(UserACL.ADMIN));
    }
}
