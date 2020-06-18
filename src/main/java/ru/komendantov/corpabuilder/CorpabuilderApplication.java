package ru.komendantov.corpabuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@CrossOrigin( origins = "*" )
public class CorpabuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorpabuilderApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
