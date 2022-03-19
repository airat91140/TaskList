package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
}
