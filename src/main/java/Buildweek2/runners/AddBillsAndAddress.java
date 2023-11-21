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
@Order(3)
public class AddBillsAndAddress implements CommandLineRunner {
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
        if (billService.getAllBills().isEmpty()) {
            List<Client> clientList = clientService.getAllClients();
            List<User> userList = userService.getAllUsers();
            int userListSize = userList.size();
            for (Client client : clientList) {
                int m = new Random().nextInt(0, userListSize);
                BillDTO randomBill;
                int firstYear = client.getInsertDate().toInstant().atZone(ZoneId.systemDefault()).getYear();
                int lastYear = client.getLastContractDate().getYear();
                for (int i = 0; i <= lastYear - firstYear; i++) {
                    LocalDate billDate = Date.from(client.getInsertDate().toInstant().atZone(ZoneId.systemDefault())
                                    .toLocalDate().plusYears(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (i == lastYear - firstYear) {
                        randomBill = new BillDTO(new Random().nextLong(1000000L, 100000000000L), billDate, client.getId(), BillState.UNPAID);
                    } else {
                        randomBill = new BillDTO(new Random().nextLong(1000000L, 100000000000L), billDate, client.getId(), BillState.PAID);
                    }
                    billService.save(userList.get(m).getId(), randomBill);
                }

            }
        }
    }
}
