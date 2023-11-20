package Buildweek2.client;

import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepo;

    public Client save(NewClientDTO body){
        Client newClient = new Client();
        newClient.setCompanyLogo(body.companyLogo());
        newClient.setBusinessName(body.businessName());
        newClient.setAnnualTurnHover(body.annualTurnHover());
        newClient.setContactName(body.contactName());
        newClient.setContactEmail(body.contactEmail());
        newClient.setContactSurname(body.contactSurname());
        newClient.setContactPhone(body.contactPhone());
        newClient.setEmail(body.email());
        newClient.setPec(body.pec());
        newClient.setPhone(body.phone());
        newClient.setInsertDate(LocalDate.now());
        newClient.setVATNumber(body.VATNumber());
        clientRepo.save(newClient);
        return newClient;
    }
    public Page<Client> getAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return clientRepo.findAll(pageable);
    }
    public Client getSingleClient(long id){
        return clientRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(""));
    }
    public void removeClient(long id){
        Client toRemove = clientRepo.findById(id).orElseThrow(()-> new ItemNotFoundException(""));
        clientRepo.delete(toRemove);
    }
}
