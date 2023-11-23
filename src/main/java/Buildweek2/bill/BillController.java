package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.bill.Payloads.FindByPartialCompanyNameDTO;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.NotContextException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")

public class BillController {
  @Autowired
  private BillService billService;

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Bill save(@AuthenticationPrincipal User admin, @RequestBody @Validated BillDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("", validation.getAllErrors());
    } else {
      return billService.save(admin.getId(), body);
    }
  }

  @GetMapping("/{id}")
  public Bill getBill(@PathVariable Long id) throws NotFoundException {
    return billService.findById(id);
  }

  @GetMapping("")
  public Page<Bill> getBill(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "id") String orderBy) throws NotContextException {
    return billService.getBill(page, size, orderBy);
  }

  @GetMapping("/clientBills/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Page<Bill> getBillsByClientId(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) throws NotContextException {
    return billService.getBillsByClientId(id, page, size, orderBy);
  }

  @GetMapping("/BillsListByClient/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getBillsListForClientId(@PathVariable("id") long id) {
    return billService.getBillsListForClient(id);
  }


  @GetMapping("/paidBills")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getPaidBills() throws NotContextException {
    return billService.getPaidBills();
  }

  @GetMapping("/unpaidBills")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getUnpaidBills() throws NotContextException {
    return billService.getUnpaidBills();
  }

  @GetMapping("/billsPerDates")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getBillsByDate(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
    return billService.getBillsByDate(startDate, endDate);
  }

  @GetMapping("/billsPerYear")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getBillsByYears(@RequestParam("year") int year) {
    return billService.getBillsByYear(year);
  }

  @GetMapping("/billsPerAmounts")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Bill> getBillsByAmounts(@RequestParam("minAmount") Long minAmount, @RequestParam("maxAmount") Long maxAmount) {
    return billService.findByRangeAmount(minAmount, maxAmount);
  }

  @GetMapping("/billsPerCompany")
  @PreAuthorize("hasAuthority('ADMIN')")
  public FindByPartialCompanyNameDTO getBillsByPartialCompanyName(@RequestParam("name") String name) {
    return billService.findByPartialCompanyName(name);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Bill changeStateBill(@PathVariable long id, @RequestBody @Validated BillPachDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException(" ", validation.getAllErrors());
    }
    return billService.changeStateBill(id, body);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
  public void findAndDeleteById(@PathVariable long id) {
    billService.findAndDeleteById(id);
  }

}
