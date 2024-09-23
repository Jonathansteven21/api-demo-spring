package online.demo.api.config;

import online.demo.api.user.User;
import online.demo.api.user.Role;
import online.demo.api.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserService userService, @Value("${app.defaultUser.username.admin}") String username, @Value("${app.defaultUser.password.admin}") String password, @Value("${app.defaultUser.username.image}") String usernameImage, @Value("${app.defaultUser.password.image}") String passwordImage) {
        return args -> {
            if (userService.count() == 0) {
                userService.createUser(new User(1L,username, password, Role.ADMIN));
                userService.createUser(new User(2L,usernameImage, passwordImage, Role.IMAGE_EDITOR));
            }
        };
    }

}
