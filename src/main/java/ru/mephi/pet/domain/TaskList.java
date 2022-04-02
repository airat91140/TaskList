package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    @OneToMany
    private Set<Record> records;
    @ManyToMany
    private Set<Tag> tags;
    @PastOrPresent
    @Column(updatable = false)
    private LocalDateTime creatingTime;
    @OneToMany
    private Set<UserGroupACL> users;

    public TaskList() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskList)) return false;
        TaskList list = (TaskList) o;
        return id.equals(list.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
