package ru.mephi.pet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.pet.domain.Record;

@Repository
public interface RecordRepository extends PagingAndSortingRepository<Record, Long> {
}
