package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.TaskList;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Long> {
}
