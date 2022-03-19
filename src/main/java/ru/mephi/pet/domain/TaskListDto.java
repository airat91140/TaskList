package ru.mephi.pet.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TaskListDto implements Serializable {
    private final Long id;
    private final String header;
    private final LocalDateTime creatingTime;
}
