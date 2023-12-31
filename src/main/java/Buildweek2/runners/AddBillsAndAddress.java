package Buildweek2.runners;

import Buildweek2.address.AddressesService;
import Buildweek2.address.Province.Province;
import Buildweek2.address.Province.ProvincesService;
import Buildweek2.address.municipality.Municipality;
import Buildweek2.address.payloads.AddressDTO;
import Buildweek2.bill.Bill;
import Buildweek2.bill.BillService;
import Buildweek2.bill.BillState;
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
    private ProvincesService provincesService;
    @Autowired
    private AddressesService addressesService;
    @Autowired
    private Faker faker;

    @Override
    public void run(String... args) throws Exception {
        if (billService.getAllBills().isEmpty()) {
            List<Province> provinceList = provincesService.getAllProvinces();
            List<Client> clientList = clientService.getAllClients();
            List<User> userList = userService.getAllAdmins();
            for (Client client : clientList) {
                int repetition = new Random().nextInt(1, 5);
                int m = new Random().nextInt(0, userList.size());
                int n = new Random().nextInt(0, provinceList.size());
                List<Municipality> municipalityList = provinceList.get(n).getMunicipalityList();
                int l = new Random().nextInt(0, municipalityList.size());
                int firstYear = client.getInsertDate().toInstant().atZone(ZoneId.systemDefault()).getYear();
                int lastYear = client.getLastContractDate().getYear();
                for (int i = 0; i <= lastYear - firstYear; i++) {
                    Bill randomBill = new Bill();
                    LocalDate billDate = Date.from(client.getInsertDate().toInstant().atZone(ZoneId.systemDefault())
                                    .toLocalDate().plusYears(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (i == lastYear - firstYear) {
                        randomBill.setDate(billDate);
                        randomBill.setAmount(new Random().nextLong(1000L, 10000L));
                        randomBill.setUser(userList.get(m));
                        randomBill.setClient(client);
                        randomBill.setNumber(i + 1);
                        randomBill.setState(BillState.UNPAID);
                    } else {
                        randomBill.setDate(billDate);
                        randomBill.setAmount(new Random().nextLong(1000L, 10000L));
                        randomBill.setUser(userList.get(m));
                        randomBill.setClient(client);
                        randomBill.setNumber(i + 1);
                        randomBill.setState(BillState.PAID);
                    }
                    billService.runnerSave(randomBill);
                }
                Municipality municipality = municipalityList.get(l);
                for (int i = 0; i < repetition; i++) {
                    AddressDTO randomAdress = new AddressDTO(faker.address().streetName(), faker.address().streetAddressNumber(), faker.address().firstName(), faker.address().zipCode(), municipality.getId(), client.getId());
                    addressesService.save(randomAdress);
                }
            }
        }
    }
}
