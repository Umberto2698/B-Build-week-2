package Buildweek2.client;

import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.client.payloads.NewClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("")
    public Page<Client> getPagesOfClients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "deviceStatus") String orderBy){
        return clientService.getAll(page, size, orderBy);
    }
    @GetMapping("{id}")
    public Client getSingleClient(@PathVariable("id") long id){
        return clientService.getSingleClient(id);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClient(@PathVariable("id") long id){
        clientService.removeClient(id);
    }
    @PutMapping("/changeClientInfo/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public Client changeClientInfo(@PathVariable("clientId") long id, @RequestBody ChangeClientInfoDTO body){
        return clientService.findByIdAndUpdate(id, body);
    }

}
