package Buildweek2.runners;

import Buildweek2.authorization.AuthService;
import Buildweek2.client.BusinessName;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.user.payloads.UserDTO;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.Random;

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
            String userName = faker.name().firstName();
            String userSurname = faker.name().lastName();
            UserDTO randomUser = new UserDTO(userName, userSurname, faker.phoneNumber().cellPhone(), userName + "." + userSurname + "@gmail.com", faker.funnyName().name());
            authService.save(randomUser);

            String clientName = faker.name().firstName();
            String clientSurname = faker.name().lastName();
            String companyName = faker.company().name();
            NewClientDTO randomClient;
            if (i >= 13) {
                randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", Long.parseLong(faker.phoneNumber().phoneNumber()), Long.parseLong(faker.phoneNumber().phoneNumber())
                        , companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                        , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SAS);
            } else if (i < 13 && i >= 7) {
                randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", Long.parseLong(faker.phoneNumber().phoneNumber()), Long.parseLong(faker.phoneNumber().phoneNumber())
                        , companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                        , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SPA);
            } else {
                randomClient = new NewClientDTO(clientName, clientSurname, clientName + "." + clientSurname + "@gmail.com", Long.parseLong(faker.phoneNumber().phoneNumber()), Long.parseLong(faker.phoneNumber().phoneNumber())
                        , companyName, String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), companyName.replaceAll(" ", "") + "@gmail.com"
                        , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SRL);
            }
            authService.save(randomClient);
        }
    }
}
