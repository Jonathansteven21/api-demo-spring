package online.lcelectronics.api.config;

import online.lcelectronics.api.user.User;
import online.lcelectronics.api.user.Role;
import online.lcelectronics.api.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserService userService, @Value("${app.defaultUser.username}") String username, @Value("${app.defaultUser.password}") String password) {
        return args -> {
            if (userService.count() == 0) {
                userService.createUser(new User(null,username, password, Role.ADMIN));
            }
        };
    }

}
