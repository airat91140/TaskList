package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
