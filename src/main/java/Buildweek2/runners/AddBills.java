package Buildweek2.runners;

import Buildweek2.bill.BillService;
import Buildweek2.bill.BillState;
import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.user.User;
import Buildweek2.user.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Order(2)
public class AddBills implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BillService billService;

    @Autowired
    private Faker faker;

    @Override
    public void run(String... args) throws Exception {
        List<Client> clientList = clientService.getAllClients();
        List<User> userList = userService.getAllUsers();
        int clientListSize = clientList.size();
        int userListSize = userList.size();
        for (int i = 0; i < 100; i++) {
            int n = new Random().nextInt(0, clientListSize);
            int m = new Random().nextInt(0, userListSize);
            BillDTO randomBill;
            LocalDate randomDate = faker.date().between(Date.from(LocalDate.now().minusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (i <= 50) {
                randomBill = new BillDTO(new Random().nextLong(1000000L, 100000000000L), randomDate, clientList.get(n).getId(), BillState.UNPAID);
            } else {
                randomBill = new BillDTO(new Random().nextLong(1000000L, 100000000000L), randomDate, clientList.get(n).getId(), BillState.PAID);
            }
            billService.save(userList.get(m).getId(), randomBill);
        }
    }
}
