package Buildweek2.client;

import Buildweek2.client.payloads.NewClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Client newClient(@RequestBody NewClientDTO body){
        return clientService.save(body);
    }
    

}
