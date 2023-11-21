package Buildweek2.runners;

import Buildweek2.authorization.AuthService;
import Buildweek2.user.payloads.UserDTO;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

//@Component
@Order(1)
public class AddClientsAndUsers implements CommandLineRunner {
  @Autowired
  private Faker faker;
  @Autowired
  private AuthService authService;

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 20; i++) {
      String name = faker.name().firstName();
      String surname = faker.name().lastName();
      UserDTO randomUser = new UserDTO(name, surname, faker.phoneNumber().cellPhone(), name + "." + surname + "@gmail.com", faker.funnyName().name());
      authService.save(randomUser);
    }
  }
}
