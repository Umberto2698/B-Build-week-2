package Buildweek2.runners;

import Buildweek2.authorization.AuthService;
import Buildweek2.client.BusinessName;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.user.UserService;
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
@Order(1)
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
            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 20; i++) {
                String userName = faker.name().firstName();
                String userSurname = faker.name().lastName();
                UserDTO randomUser = new UserDTO(userName, userSurname, faker.phoneNumber().cellPhone(), userName + "." + userSurname + "@gmail.com", faker.funnyName().name());
                authService.save(randomUser);
            }
            for (int i = 0; i < 80; i++) {
                String clientName = faker.name().firstName();
                String clientSurname = faker.name().lastName();
                String companyName = faker.company().name();
                Date randomDate = faker.date().between(Date.from(LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.now().minusYears(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                int randomMonth = randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getValue();
                int randomDay = randomDate.toInstant().atZone(ZoneId.systemDefault()).getDayOfMonth();
                int randomYear = new Random().nextInt(randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(), currentYear);
                LocalDate randomLastDate = LocalDate.of(randomYear, randomMonth, randomDay);
                NewClientDTO randomClient = null;
                if (i < 15) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SAS);
                } else if (i >= 15 && i < 30) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SPA);
                } else if (i >= 30 && i < 45) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, randomLastDate, companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL);
                } else if (i >= 45 && i < 60) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL);
                } else if (i >= 60 && i < 75) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL);
                } else if (i >= 75) {
                    randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                            , randomDate, LocalDate.of(currentYear, randomMonth, randomDay), companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                            , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL);
                }
                authService.save(randomClient);
            }
        }
    }
}

