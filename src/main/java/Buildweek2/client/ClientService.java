package Buildweek2.client;

import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.user.User;
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

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepo;
    @Autowired
    Cloudinary cloudinary;

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
    public Client uploadLogo(MultipartFile file, long id) throws IOException {
        Client found = this.getSingleClient(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setCompanyLogo(url);
        return clientRepo.save(found);
    }
}
