package Buildweek2.bill;

import Buildweek2.bill.Payloads.BillPachDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills")
public class BillController {
    @Autowired
    BillService billService;
    @GetMapping("")
    public Page<Bill> getBill(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return billService.getBill(page, size, orderBy);
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
    @GetMapping("/clientbill/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
        public Page<Bill> getBillsByClientId(@PathVariable("id") long id,@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String orderBy){
        return billService.getBillsByClientId(id,page, size, orderBy);
    }

}
