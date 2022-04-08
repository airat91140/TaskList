package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<Record> records;
    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Tag> tags;
    @PastOrPresent
    @Column(updatable = false)
    private LocalDateTime creatingTime;
    @ManyToOne
    @JoinColumn
    private Group owner;

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
