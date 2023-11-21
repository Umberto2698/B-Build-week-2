package Buildweek2.runners;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    @Autowired
    private Faker faker;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(faker.company().bs());
        System.out.println(faker.company().logo());
        System.out.println(faker.company().industry());
    }
}
