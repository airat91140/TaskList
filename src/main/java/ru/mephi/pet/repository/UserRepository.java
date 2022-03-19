package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
