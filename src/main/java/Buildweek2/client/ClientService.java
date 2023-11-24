package Buildweek2.client;

import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepo;
    @Autowired
    Cloudinary cloudinary;

    public Page<Client> getAll(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return clientRepo.findAll(pageable);
    }

    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

//    public List<Client> getClientFromUserId(long userId) {
//        return clientRepo.ClientFromUserId(userId);
//    }

    public Client getSingleClient(long id) {
        return clientRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void removeClient(long id) {
        Client toRemove = this.getSingleClient(id);
        clientRepo.delete(toRemove);
    }

    public Client findByIdAndUpdate(long id, ChangeClientInfoDTO body) {
        Client found = this.getSingleClient(id);
        found.setContactName(body.contactName());
        found.setContactSurname(body.contactSurname());
        found.setContactPhone(body.contactPhone());
        found.setEmail(body.email());
        found.setPhone(body.phone());
        found.setPec(body.pec());
        return clientRepo.save(found);
    }

    public Client uploadLogo(MultipartFile file, long id) throws IOException {
        Client found = this.getSingleClient(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setCompanyLogo(url);
        return clientRepo.save(found);
    }

    public List<Client> filterByTurnHover(long startAnnualTurnHover, long endAnnualTurnHover) {
        if (startAnnualTurnHover <= endAnnualTurnHover) {
            return clientRepo.filterByTurnHover(startAnnualTurnHover, endAnnualTurnHover).orElseThrow(() -> new NotFoundException("Nessun record per questo fatturato"));
        } else {
            return clientRepo.filterByTurnHover(endAnnualTurnHover, startAnnualTurnHover).orElseThrow(() -> new NotFoundException("Nessun record per questo fatturato"));
        }
    }

    public List<Client> filterByInsertDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(endDate)) {
            return clientRepo.filterByInsertDate(startDate, endDate).orElseThrow(() -> new NotFoundException("Nessun record per queste date"));
        } else {
            return clientRepo.filterByInsertDate(endDate, startDate).orElseThrow(() -> new NotFoundException("Nessun record per queste date"));
        }
    }

    public List<Client> filterByLastContractDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(endDate)) {
            return clientRepo.filterByLastContractDate(startDate, endDate).orElseThrow(() -> new NotFoundException("Nessun record per queste date"));
        } else {
            return clientRepo.filterByLastContractDate(endDate, startDate).orElseThrow(() -> new NotFoundException("Nessun record per queste date"));
        }
    }

    public List<Client> findByCompanyNameStartingWith(String partialName) {
        if (partialName.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Client> clientList = clientRepo.filterByPartialName(partialName).orElseThrow(() -> new NotFoundException("No company with this name."));
            System.out.println(clientList.size());
            clientList.forEach(System.out::println);
            return clientList;
        }
    }
}
