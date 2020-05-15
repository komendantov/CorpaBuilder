package ru.komendantov.corpabuilder.auth.services;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserDetailsImpl;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public UpdateResult updateUser(String username, User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        if (user.getUsername() != null && !user.getUsername().isEmpty())
            update.set("username", user.getUsername());
        if (user.getEmail() != null && !user.getEmail().isEmpty())
            update.set("email", user.getEmail());
        if (user.getPassword()!=null && !user.getPassword().isEmpty())
            update.set("password", user.getPassword());
        if (user.getUserSettings().getReplaces()!=null && !user.getUserSettings().getReplaces().isEmpty()) {
            update.set("userSettings.replaces", user.getUserSettings().getReplaces());
        }
        return mongoTemplate.upsert(query, update, User.class);
    }

}