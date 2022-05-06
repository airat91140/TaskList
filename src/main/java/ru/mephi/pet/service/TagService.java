package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.*;
import ru.mephi.pet.exception.ForbiddenOperationException;
import ru.mephi.pet.exception.NotFoundException;
import ru.mephi.pet.repository.TagRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TaskListMapper taskListMapper;
    private final UserRepository userRepository;

    public Iterable<TagDto> getTags() {
        List<TagDto> result = new LinkedList<>();
        tagRepository.findAll().forEach(t -> result.add(tagMapper.toDto(t)));
        return result;
    }

    public Iterable<TagDto> getTags(String login) {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new)
                .getTasks().stream()
                .flatMap(t -> t.getTags().stream())
                .distinct()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    public TagDto getTag(Long id) {
        return tagMapper.toDto(tagRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public TagDto getTag(String login, Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(NotFoundException::new);
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        if (tag.getLists().stream().anyMatch(l -> TaskListService.canRead(l, user)))
            throw new ForbiddenOperationException();
        return tagMapper.toDto(tag);
    }

    public Iterable<TaskListDto> getLists(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(NotFoundException::new)
                .getLists()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toList());
    }

    public Iterable<TaskListDto> getLists(String login, Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(NotFoundException::new);
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        if (tag.getLists().stream().anyMatch(l -> TaskListService.canRead(l, user)))
            throw new ForbiddenOperationException();
        return tag.getLists()
                .stream()
                .map(taskListMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateTag(String login, Long id, TagDto tagDto) {
        Tag tag = tagRepository.findById(id).orElseThrow(NotFoundException::new);
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        if (tag.getLists().stream().anyMatch(l -> TaskListService.canRead(l, user)))
            throw new ForbiddenOperationException();
        tag.setData(tagDto.getData());
        tagRepository.save(tag);
    }
}
