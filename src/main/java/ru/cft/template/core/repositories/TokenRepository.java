package ru.cft.template.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.api.model.Token;

import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {
    Token findBySessionId(Long id);
}
