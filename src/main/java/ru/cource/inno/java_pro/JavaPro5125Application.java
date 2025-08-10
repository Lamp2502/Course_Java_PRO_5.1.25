package ru.cource.inno.java_pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.cource.inno.java_pro.hw4_user_app.User;
import ru.cource.inno.java_pro.hw4_user_app.UserService;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JavaPro5125Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx =
                 SpringApplication.run(JavaPro5125Application.class, args)) {

            UserService users = ctx.getBean(UserService.class);

            // CREATE
            User u1 = users.create("alice");
            User u2 = users.create("bob");
            System.out.println("Created: " + u1);
            System.out.println("Created: " + u2);

            // READ (one)
            Optional<User> byId = users.findById(u1.getId()); // или users.findById(...) если так названо
            System.out.println("Get by id: " + byId.orElse(null));

            // READ (all)
            List<User> all = users.getAll();
            System.out.println("All: " + all);

            // UPDATE
            boolean renamed = users.rename(u2.getId(), "bobby");
            System.out.println("Renamed bob -> bobby? " + renamed);
            System.out.println("bobby: " + users.findByUsername("bobby").orElse(null));

            // DELETE
            boolean deleted = users.delete(u1.getId());
            System.out.println("Deleted alice? " + deleted);
            System.out.println("All after delete: " + users.getAll());
        }
    }
}
