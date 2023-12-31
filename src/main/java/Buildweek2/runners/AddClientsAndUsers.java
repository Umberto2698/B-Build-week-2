package Buildweek2.runners;

import Buildweek2.authorization.AuthService;
import Buildweek2.client.BusinessName;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.user.User;
import Buildweek2.user.UserRole;
import Buildweek2.user.UserService;
import Buildweek2.user.payloads.RoleUpdateDTO;
import Buildweek2.user.payloads.UserDTO;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Component
@Order(2)
public class AddClientsAndUsers implements CommandLineRunner {
  @Autowired
  private Faker faker;
  @Autowired
  private AuthService authService;

  @Autowired
  private UserService userService;

  @Override
  public void run(String... args) throws Exception {
    if (userService.getAllUsers().isEmpty()) {
      UserDTO admin = new UserDTO("admin", "admin", "admin", "admin@gmail.com", "admin");
      UserDTO userDTO = new UserDTO("user", "user", "user", "user@gmail.com", "user");
      authService.save(admin);
      User user = authService.save(userDTO);
      authService.updateRole(user.getId(), new RoleUpdateDTO(UserRole.USER.name()));
      int currentYear = LocalDate.now().getYear();
      for (int i = 0; i < 18; i++) {
        String userName = faker.name().firstName();
        String userSurname = faker.name().lastName();
        UserDTO randomUser = new UserDTO(userName, userSurname, faker.phoneNumber().cellPhone(), userName + "." + userSurname + "@gmail.com", faker.funnyName().name());
        User user1 = authService.save(randomUser);
        if (i < 9) {
          authService.updateRole(user1.getId(), new RoleUpdateDTO(UserRole.USER.name()));
        }
      }
      for (int i = 0; i < 80; i++) {
        String clientName = faker.name().firstName();
        String clientSurname = faker.name().lastName();
        String companyName = faker.company().name();
        Date randomDate = faker.date().between(Date.from(LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.now().minusYears(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        int randomMonth = randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getValue();
        int randomDay;
        if (randomMonth == 2) {
          randomDay = new Random().nextInt(1, 28);
        } else if (randomMonth == 11 || randomMonth == 9 || randomMonth == 4 || randomMonth == 6) {
          randomDay = new Random().nextInt(1, 30);
        } else {
          randomDay = new Random().nextInt(1, 31);
        }
        int randomYear = new Random().nextInt(randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), currentYear);
        LocalDate randomLastDate = LocalDate.of(randomYear, randomMonth, randomDay);
        NewClientDTO randomClient = null;
        if (i < 15) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SAS.name());
        } else if (i >= 15 && i < 30) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SPA.name());
        } else if (i >= 30 && i < 45) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL.name());
        } else if (i >= 45 && i < 60) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL.name());
        } else if (i >= 60 && i < 75) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL.name());
        } else if (i >= 75) {
          randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                  , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                  , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL.name());
        }
        authService.save(randomClient);
      }
    }
  }
}

