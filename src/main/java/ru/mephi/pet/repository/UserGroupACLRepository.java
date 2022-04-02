package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.UserGroupACL;

@Repository
public interface UserGroupACLRepository extends CrudRepository<UserGroupACL, Long> {
}
