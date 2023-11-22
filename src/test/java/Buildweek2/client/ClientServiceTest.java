package Buildweek2.client;

import Buildweek2.authorization.AuthService;
import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.NotFoundException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @Autowired
    private Faker faker;

    private Client client;

    private NewClientDTO newClientDTO;

    @BeforeEach
    public void Setup() {
        newClientDTO = new NewClientDTO("clientName", "clientSurname", "a@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                , new Date(), LocalDate.now(), "companyName", String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), "a@gmail.com"
                , new Random().nextLong(10000000L, 100000000000L), faker.commerce().color() + "@pec.it", faker.company().logo(), BusinessName.SAS.name());
        client = authService.save(newClientDTO);
    }


    @Test
    public void userIsNotNull() {
        Assertions.assertNotNull(client);
    }

    @Test
    public void FindByIdReturnNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> clientService.getSingleClient(-1L));
    }

    @Test
    public void FindByIdAndReturnCorrectUser() {
        Client found = clientService.getSingleClient(client.getId());
        Assertions.assertEquals(client, found);
    }

    @Test
    public void FindByIdAndUpdateReturnUpdatedUser() {
        ChangeClientInfoDTO body = new ChangeClientInfoDTO("clientName", "clientSurname", "a@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber(),
                "companyName@gmail.com", faker.commerce().color() + "@pec.it");
        Client updateClient = clientService.findByIdAndUpdate(client.getId(), body);
        Assertions.assertNotEquals(client, updateClient);
    }

    @Test
    public void DeleteByIdAndReturnNotFound() {
        clientService.removeClient(client.getId());
        Assertions.assertThrows(NotFoundException.class, () -> clientService.getSingleClient(client.getId()));
        client = null;
    }

    @Test
    public void FilterByAnnualTurnHover() {
        int foundSize = clientService.filterByTurnHover(73945629011L).size();
        Assertions.assertEquals(1, foundSize);
    }

    @Test
    public void FilterByInsertDate() {
        int foundSize = clientService.filterByInsertDate(LocalDate.now().minusYears(1), LocalDate.now()).size();
        Assertions.assertEquals(1, foundSize);
    }

    @Test
    public void FilterByLastContractData() {
        List<Client> clientList = clientService.filterByLastContractDate(LocalDate.now().minusYears(1), LocalDate.now().plusDays(2));
        Assertions.assertEquals(35, clientList.size());
    }

    @AfterEach
    public void deleteAll() {
        if (client != null) {
            clientService.removeClient(client.getId());
        }
    }
}
