package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.client.Client;
import Buildweek2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public class BillService {
    @Autowired
    private BillRepository billRepository;

    /* @Autowired
    private UtenteRepository utenteRepository;*/

    /* @Autowired
    private ClientRepository clientRepository;*/

    public Bill save(BillDTO body) {
        Bill newBill = new Bill();

        newBill.setAmount(body.amount());
        newBill.setState(body.billState());
        return billRepository.save(newBill);
    }
    public Page<Bill> getBill(int page, int size, String orderBy){
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
    public List<Bill> getBillsByCustomer(Long clientId) {
        return billRepository.findByClientId(clientId).orElseThrow(()->new NotFoundException(clientId));
    }
   public List<Bill> billsPaidUnPaid (BillState state) {

       return  billRepository.findByState(state).orElseThrow(()->new NotFoundException("no Record"));
   };

    public List<Bill> getBillsByDate(LocalDate startDate,LocalDate endDate) {
        return billRepository.findByDate(startDate,endDate).orElseThrow(()->new NotFoundException("no Record"));
    }
    public List<Bill> getBillsByYear(LocalDate date) {
        int year = date.getYear();
        return billRepository.findByDateYear(year).orElseThrow(()->new NotFoundException("no Record"));
    }
    public List<Bill> findByRangeAmount(Long minAmount,Long maxAmount) {

        return billRepository.findByRangeAmount(minAmount,maxAmount).orElseThrow(()->new NotFoundException("no Record"));
    }
}
