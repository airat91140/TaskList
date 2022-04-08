package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Groups")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = {javax.persistence.CascadeType.ALL})
    private Set<TaskList> tasks = new java.util.LinkedHashSet<>();
    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<User> users;
    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private Set<UserGroupACL> UserACLS = new java.util.LinkedHashSet<>();

    public Group() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return id.equals(group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
