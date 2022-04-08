package ru.mephi.pet.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.Group;
import ru.mephi.pet.domain.User;
import ru.mephi.pet.domain.UserGroupACL;

@Repository
public interface UserGroupACLRepository extends CrudRepository<UserGroupACL, Long>, JpaSpecificationExecutor<UserGroupACL> {
    public UserGroupACL findUserGroupACLByGroupAndUser(Group group, User user);
}
