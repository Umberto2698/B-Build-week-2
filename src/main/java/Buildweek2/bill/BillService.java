package Buildweek2.bill;

import Buildweek2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public Bill findAndUpdateById(int id, BillPachDTO body) {
        Bill found1 = this.findById(id);


        found1.setState(body.billState());



        return found1;

    }

}
