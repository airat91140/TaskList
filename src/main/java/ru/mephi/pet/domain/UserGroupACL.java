package ru.mephi.pet.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mephi.pet.enums.UserACL;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserGroupACL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Group group;
    private UserACL userACL;
    public UserGroupACL() {
    }
}
