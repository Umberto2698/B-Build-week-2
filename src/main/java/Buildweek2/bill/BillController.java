package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.bill.Payloads.FindByPartialCompanyNameDTO;
import Buildweek2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public Bill changeStateBill(@PathVariable int id, @RequestBody @Validated BillPachDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(" ", validation.getAllErrors());
        }
        return billService.changeStateBill(id, body);
    }

    @GetMapping("/billpaidunpaid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getBillsByPaidUnpaid(@RequestParam("state") @Validated BillPachDTO state, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("", validation.getAllErrors());
        }
        return billService.billsPaidUnPaid(state);
    }

    @GetMapping("/billperdate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getBillsByDate(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        return billService.getBillsByDate(startDate, endDate);
    }

    @GetMapping("/billperyears")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getBillsByYears(@RequestParam("data") LocalDate data) {
        return billService.getBillsByYear(data);
    }

    @GetMapping("/billAmounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getBillsByAmounts(@RequestParam("minAmount") Long minAmount, @RequestParam("maxAmount") Long maxAmount) {
        return billService.findByRangeAmount(minAmount, maxAmount);
    }

    @GetMapping("/billCompany")
    @PreAuthorize("hasAuthority('ADMIN')")
    public FindByPartialCompanyNameDTO getBillsByPartialCompanyName(@RequestParam("name") String name) {
        return billService.findByPartialCompanyName(name);
    }
}
