package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    private String password;
    private String email;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = {javax.persistence.CascadeType.ALL})
    private Set<TaskList> tasks = new java.util.LinkedHashSet<>();
    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Group> groups;
    @OneToMany(cascade = {javax.persistence.CascadeType.ALL})
    private Set<UserGroupACL> groupACLS = new java.util.LinkedHashSet<>();
    @ColumnDefault("ROLE_USER")
    private String role;

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
