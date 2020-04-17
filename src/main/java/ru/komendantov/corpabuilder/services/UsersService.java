package ru.komendantov.corpabuilder.services;

import com.mongodb.operation.UserExistsOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ru.komendantov.corpabuilder.exceptions.EmailExistsException;
import ru.komendantov.corpabuilder.models.User;
import ru.komendantov.corpabuilder.repositories.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String login, String password) {
        Optional<User> user = memberRepository.login(login, passwordEncoder.encode(password));
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            User use = user.get();
            use.setToken(token);
            memberRepository.save(use);
            return token;
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    public Optional findByToken(String token) {
        Optional<User> user = memberRepository.findByToken(token);
        if (user.isPresent()) {
            User user1 = user.get();
            org.springframework.security.core.userdetails.User user2 = new org.springframework.security.core.userdetails.User(user1.getLogin(), user1.getPassword(), true, true,
                    true, true, AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional findByLogin(String login) {
        Optional<User> member = memberRepository.findByLogin(login);
        return Optional.of(member);
    }

    public User register(User user) throws EmailExistsException {

        if(memberRepository.findByLogin(user.getLogin()).isPresent())
            throw new EmailExistsException();
        return memberRepository.save(user);
    }
}
