package ru.komendantov.corpabuilder.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.entity.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {
}
