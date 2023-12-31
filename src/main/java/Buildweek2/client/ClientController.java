package Buildweek2.client;

import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;


    @GetMapping("")
    public Page<Client> getPagesOfClients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "companyName") String orderBy) {
        return clientService.getAll(page, size, orderBy);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client getSingleClient(@PathVariable("id") long id) {
        return clientService.getSingleClient(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClient(@PathVariable("id") long id) {
        clientService.removeClient(id);
    }

    @PutMapping("/changeclientinfo/{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Client changeClientInfo(@PathVariable("clientId") long id, @RequestBody @Validated ChangeClientInfoDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("", validation.getAllErrors());
        }
        return clientService.findByIdAndUpdate(id, body);
    }

    @PatchMapping("/upload/me")
    public Client updateProfilePicture(@RequestParam("avatar") MultipartFile body, @AuthenticationPrincipal User currentUser) throws IOException {
        return clientService.uploadLogo(body, currentUser.getId());
    }

    @GetMapping("/turnhover")
    public List<Client> getByTurnHover(@RequestParam("startAmount") long startAmount, @RequestParam("endAmount") long endAmount) {
        return clientService.filterByTurnHover(startAmount, endAmount);
    }

    @GetMapping("/date")
    public List<Client> getByRangeInsertDate(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        return clientService.filterByInsertDate(startDate, endDate);
    }

    @GetMapping("/lastcontract")
    public List<Client> getByRangeLastContract(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        return clientService.filterByLastContractDate(startDate, endDate);
    }

    @GetMapping("/companyname")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> findByCompanyNameStartingWith(@RequestParam("name") String body) {
        return clientService.findByCompanyNameStartingWith(body);
    }
}
