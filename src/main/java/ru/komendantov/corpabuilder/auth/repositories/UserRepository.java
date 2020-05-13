package ru.komendantov.corpabuilder.auth.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.komendantov.corpabuilder.auth.models.User;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> getById(String id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> getByUsername(String username);
    //Optional<Role> findByRole(ERole role);
}