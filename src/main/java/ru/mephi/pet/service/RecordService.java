package ru.mephi.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mephi.pet.domain.Record;
import ru.mephi.pet.domain.RecordDto;
import ru.mephi.pet.domain.TaskListDto;
import ru.mephi.pet.repository.RecordRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final TaskListMapper taskListMapper;

    public Iterable<RecordDto> getRecords() {
        List<RecordDto> result = new LinkedList<>();
        recordRepository.findAll().forEach(t -> result.add(recordMapper.toDto(t)));
        return result;
    }

    public RecordDto getRecord(Long id) {
        return recordMapper.toDto(recordRepository.findById(id).orElseThrow());
    }

    public TaskListDto getList(Long id) {
        return taskListMapper.toDto(recordRepository.findById(id).orElseThrow().getParentList());
    }

    public void updateDeadline(Long id, LocalDateTime newTime) {
        Record record = recordRepository.findById(id).orElseThrow();
        record.setDeadLine(newTime);
        recordRepository.save(record);
    }

    public void updateData(Long id, String data) {
        Record record = recordRepository.findById(id).orElseThrow();
        record.setData(data);
        recordRepository.save(record);
    }

    public void updateIsDone(Long id, Boolean isDone) {
        Record record = recordRepository.findById(id).orElseThrow();
        record.setIsDone(isDone);
        recordRepository.save(record);
    }
}
