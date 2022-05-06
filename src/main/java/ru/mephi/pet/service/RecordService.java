package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.Record;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.exception.ForbiddenOperationException;
import ru.mephi.pet.exception.NotFoundException;
import ru.mephi.pet.repository.RecordRepository;
import ru.mephi.pet.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final TaskListMapper taskListMapper;
    private final UserRepository userRepository;

    public Iterable<RecordDto> getRecords() {
        List<RecordDto> result = new LinkedList<>();
        recordRepository.findAll().forEach(t -> result.add(recordMapper.toDto(t)));
        return result;
    }

    public RecordDto getRecord(Long id) {
        return recordMapper.toDto(recordRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public RecordDto getRecord(String login, Long id) {
        Record record = recordRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!TaskListService.canRead(record.getParentList(), userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        return recordMapper.toDto(record);
    }

    public TaskListDto getList(Long id) {
        return taskListMapper.toDto(recordRepository.findById(id).orElseThrow(NotFoundException::new).getParentList());
    }

    public TaskListDto getList(String login, Long id) {
        Record record = recordRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!TaskListService.canRead(record.getParentList(), userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        return taskListMapper.toDto(record.getParentList());
    }

    public void updateRecord(String login, Long id, RecordDto recordDto) {
        Record record = recordRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!TaskListService.canRead(record.getParentList(), userRepository.findByLogin(login).orElseThrow(NotFoundException::new)))
            throw new ForbiddenOperationException();
        record.setDeadLine(recordDto.getDeadLine());
        record.setIsDone(recordDto.getIsDone());
        record.setData(recordDto.getData());
        recordRepository.save(record);
    }
}
