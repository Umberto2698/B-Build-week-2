package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.user.User;
import Buildweek2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    public Bill save(long userId, BillDTO body) {
        User user = userService.getById(userId);
        Client client = clientService.getSingleClient(body.clientId());
        Bill newBill = new Bill();
        newBill.setAmount(body.amount());
        newBill.setState(body.billState());
        newBill.setUser(user);
        newBill.setClient(client);
        newBill.setDate(body.date());
        newBill.setNumber(
                new Random().nextLong(100000000000L, 1000000000000L)
        );
        return billRepository.save(newBill);
    }

    public Page<Bill> getBill(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return billRepository.findAll(pageable);
    }

    public Bill findById(long id) {

        return billRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public Bill changeStateBill(int id, BillPachDTO body) {
        Bill found1 = this.findById(id);
        found1.setState(body.billState());
        return found1;

    }

    public void findAndDeleteById(int id) {
        Bill found1 = this.findById(id);
        billRepository.delete(found1);
    }

}
