package ru.cft.template.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.Session;
import ru.cft.template.entity.User;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findByUser(User user);
}
