package ru.mephi.pet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.Record;

@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {
}
