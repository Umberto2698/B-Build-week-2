package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("")
    public Page<Bill> getBill(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy) {
        return billService.getBill(page, size, orderBy);
    }

    @GetMapping("/clientbill/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Bill> getBillsByClientId(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String orderBy) {
        return billService.getBillsByClientId(id, page, size, orderBy);
    }

    @GetMapping("/clientbilllist/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getBillsListForClientId(@PathVariable("id") long id) {
        return billService.getBillsListForClient(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Bill save(@PathVariable long userId, @RequestBody @Validated BillDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("", validation.getAllErrors());
        } else {
            return billService.save(userId, body);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDeleteById(@PathVariable int id) {
        billService.findAndDeleteById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Bill changeStateBill(@PathVariable int id, @RequestBody BillPachDTO body) {
        return billService.changeStateBill(id, body);
    }
}
