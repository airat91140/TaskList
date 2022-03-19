package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Getter
@Setter
public class Group {
    public Group() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private Set<TaskList> tasks;
    @ManyToMany
    private Set<User> users;
}
