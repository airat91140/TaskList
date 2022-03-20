package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.Tag;
import ru.mephi.pet.domain.TagDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.repository.TagRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TaskListMapper taskListMapper;

    public Iterable<TagDto> getTags() {
        List<TagDto> result = new LinkedList<>();
        tagRepository.findAll().forEach(t -> result.add(tagMapper.toDto(t)));
        return result;
    }

    public TagDto getTag(Long id) {
        return tagMapper.toDto(tagRepository.findById(id).orElseThrow());
    }

    public Iterable<TaskListDto> getLists(Long id) {
        return tagRepository.findById(id)
                .orElseThrow()
                .getLists()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateData(Long id, String data) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setData(data);
        tagRepository.save(tag);
    }
}