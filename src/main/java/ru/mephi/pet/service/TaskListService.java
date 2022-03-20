package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.repository.RecordRepository;
import ru.mephi.pet.repository.TagRepository;
import ru.mephi.pet.repository.TaskListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final TagRepository tagRepository;
    private final RecordRepository recordRepository;
    private final TaskListMapper taskListMapper;
    private final TagMapper tagMapper;
    private final RecordMapper recordMapper;

    public Iterable<TaskListDto> getLists() {
        Iterable<TaskList> list = taskListRepository.findAll();
        List<TaskListDto> result = new LinkedList<>();
        list.forEach(t -> result.add(taskListMapper.toDto(t)));
        return result;
    }

    public TaskListDto getList(Long id) {
        return taskListMapper.toDto(taskListRepository.findById(id).orElseThrow());
    }

    public Iterable<TagDto> getTags(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow()
                .getTags()
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Iterable<RecordDto> getRecords(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow()
                .getRecords()
                .stream()
                .map(recordMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addTag(Long id, TagDto tagDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow();
        Tag tag = tagRepository.findById(tagDto.getId()).orElse(null);
        if (tag == null)
            tag = tagRepository.save(tagMapper.toEntity(tagDto));
        list.getTags().add(tag);
        tag.getLists().add(list);
        taskListRepository.save(list);
        tagRepository.save(tag);
    }

    public void addRecord(Long id, RecordDto recordDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow();
        Record record = recordRepository.save(recordMapper.toEntity(recordDto));
        list.getRecords().add(record);
        record.setParentList(list);
        taskListRepository.save(list);
        recordRepository.save(record);
    }

    public void updateHeader(Long id, String header) {
        TaskList list = taskListRepository.findById(id).orElseThrow();
        list.setHeader(header);
        taskListRepository.save(list);
    }

    public void deleteTag(Long id, TagDto tagDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow();
        list.getTags().remove(tagMapper.toEntity(tagDto));
        Tag tag = tagRepository.findById(tagDto.getId()).orElseThrow();
        tag.getLists().remove(list);
        taskListRepository.save(list);
        tagRepository.save(tag);
    }

    public void deleteRecord(Long id, RecordDto recordDto) {
        TaskList list = taskListRepository.findById(id).orElseThrow();
        list.getRecords().remove(recordMapper.toEntity(recordDto));
        recordRepository.deleteById(recordDto.getId());
        taskListRepository.save(list);
    }
}
