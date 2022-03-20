package ru.mephi.pet.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RecordDto implements Serializable {
    private final Long id;
    private final String data;
    private final Boolean isDone;
    private final LocalDateTime deadLine;
}
