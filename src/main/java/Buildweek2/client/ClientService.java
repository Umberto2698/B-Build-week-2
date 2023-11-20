package Buildweek2.client;

import Buildweek2.client.payloads.NewClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
