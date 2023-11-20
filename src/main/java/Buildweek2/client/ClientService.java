package Buildweek2.client;

import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.NotFoundException;
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

    public Page<Client> getAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return clientRepo.findAll(pageable);
    }
    public Client getSingleClient(long id){
        return clientRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public void removeClient(long id){
        Client toRemove = clientRepo.findById(id).orElseThrow(()-> new NotFoundException(id));
        clientRepo.delete(toRemove);
    }
    public Client findByIdAndUpdate(long id, ChangeClientInfoDTO body){
        Client found = clientRepo.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setContactName(body.contactName());
        found.setContactSurname(body.contactSurname());
        found.setContactPhone(body.contactPhone());
        found.setEmail(body.email());
        found.setPhone(body.phone());
        found.setPec(body.pec());
        clientRepo.save(found);
        return found;
    }
}
