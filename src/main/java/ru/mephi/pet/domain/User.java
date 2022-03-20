package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    private String password;
    private String email;
    @Column(nullable = false)
    private String name;
    @OneToMany
    private Set<TaskList> tasks;
    @ManyToMany
    private Set<Group> groups;
}
