package ru.mephi.pet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.TaskList;

@Repository
public interface TaskListRepository extends PagingAndSortingRepository<TaskList, Long> {
}
