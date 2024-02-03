package ru.cft.template.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByFirstName(String s);

    User findByPhone(Long phone);

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.sessions WHERE p.id = :id")
    User findByIdWithSessions(@Param("id") Long id);
}
