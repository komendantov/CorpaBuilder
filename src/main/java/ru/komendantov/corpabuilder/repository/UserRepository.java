package ru.komendantov.corpabuilder.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.entity.User;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
