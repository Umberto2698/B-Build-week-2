package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.bill.Payloads.FindByPartialCompanyNameDTO;
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

import javax.naming.NotContextException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {
  @Autowired
  BillRepository billRepository;

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
    newBill.setState(BillState.valueOf(body.billState()));
    newBill.setUser(user);
    newBill.setDate(LocalDate.now());
    newBill.setClient(client);
    newBill.setNumber(bills.size() + 1);
    return billRepository.save(newBill);
  }

  public Bill runnerSave(Bill body) {
    return billRepository.save(body);
  }

  public Page<Bill> getBill(int page, int size, String orderBy) throws NotContextException {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    if (pageable.getPageSize() <= 0 || pageable.isUnpaged()) {
      throw new NotContextException();
    }
    return billRepository.findAll(pageable);
  }

  public Page<Bill> getBillsByClientId(Long clientId, int page, int size, String orderBy) throws NotContextException {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    if (pageable.getPageSize() <= 0 || pageable.isUnpaged()) {
      throw new NotContextException();
    }
    return billRepository.findByClientId(clientId, pageable);
  }

  public List<Bill> getBillsListForClient(Long clientId) {
    return billRepository.findByClientId(clientId);
  }

  public List<Bill> getAllBills() {
    return billRepository.findAll();
  }

  public Bill findById(long id) {
    return billRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Bill changeStateBill(Long id, BillPachDTO body) {
    Bill found1 = this.findById(id);
    List<BillState> enumList = List.of(BillState.values());
    for (int i = 0; i < enumList.size(); i++) {
      if (enumList.get(i).name().equals(body.billState())) {
        found1.setState(enumList.get(i));
      }
    }
    return billRepository.save(found1);
  }

  public void findAndDeleteById(Long id) {
    Bill found1 = this.findById(id);
    billRepository.delete(found1);
  }

  public List<Bill> getPaidBills() throws NotContextException {
    List<Bill> paidBills = billRepository.findByState(BillState.PAID);
    if (paidBills.isEmpty()) {
      throw new NotContextException();
    } else {
      return paidBills;
    }

  }

  public List<Bill> getUnpaidBills() throws NotContextException {
    List<Bill> paidBills = billRepository.findByState(BillState.UNPAID);
    if (paidBills.isEmpty()) {
      throw new NotContextException();
    } else {
      return paidBills;
    }
  }

  public List<Bill> getBillsByDate(LocalDate startDate, LocalDate endDate) {
    return billRepository.findByDate(startDate, endDate).orElseThrow(() -> new NotFoundException("no Record"));
  }

  public List<Bill> getBillsByYear(int year) {

    return billRepository.findByDateYear(year).orElseThrow(() -> new NotFoundException("no Record"));
  }

  public List<Bill> findByRangeAmount(Long minAmount, Long maxAmount) {
    return billRepository.findByRangeAmount(minAmount, maxAmount).orElseThrow(() -> new NotFoundException("no Record"));
  }

  public FindByPartialCompanyNameDTO findByPartialCompanyName(String partialCompanyName) {
    List<Client> clientList = clientService.findByCompanyNameStartingWith(partialCompanyName);
    if (clientList.isEmpty()) throw new NotFoundException(partialCompanyName);
    List<List<Bill>> bills = new ArrayList<>();
    for (Client client : clientList) {
      List<Bill> found = this.getBillsListForClient(client.getId());
      bills.add(found);
    }
    return new FindByPartialCompanyNameDTO(bills);
  }
}
