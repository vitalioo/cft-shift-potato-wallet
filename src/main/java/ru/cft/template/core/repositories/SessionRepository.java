package ru.cft.template.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
}