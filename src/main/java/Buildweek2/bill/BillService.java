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

import java.util.List;

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
        List<Bill> bills = this.getBillsListForClient(body.clientId());
        Bill newBill = new Bill();
        newBill.setAmount(body.amount());
        newBill.setState(body.billState());
        newBill.setUser(user);
        newBill.setClient(client);
        newBill.setDate(body.date());
        newBill.setNumber(bills.size() + 1);
        return billRepository.save(newBill);
    }

    public Page<Bill> getBill(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return billRepository.findAll(pageable);
    }

    public Page<Bill> getBillsByClientId(Long clientId, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return billRepository.findByClientId(clientId, pageable);
    }

    public List<Bill> getBillsListForClient(Long clientId) {
        return billRepository.findByClientId(clientId);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill findById(long id) {

  public Page<Bill> getBill(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return billRepository.findAll(pageable);
  }

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
