package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class TaskList {
    public TaskList() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header;
    @OneToMany
    private Set<Record> records;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new LinkedHashSet<>();
    @PastOrPresent
    @Column(updatable = false)
    private LocalDateTime creatingTime;
}
