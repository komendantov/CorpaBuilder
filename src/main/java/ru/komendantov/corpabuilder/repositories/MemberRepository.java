package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.models.User;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<User, String>  {

    Optional<User> login(String username, String password);
    Optional<User> findByToken(String token);
    Optional<User> findByLogin(String login);
    Optional<User> findAllByLogin(String login);
//    Optional<Member> insert(Member member);
}
