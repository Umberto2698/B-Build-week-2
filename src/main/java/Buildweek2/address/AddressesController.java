package Buildweek2.address;


import Buildweek2.address.payloads.AddressDTO;
import Buildweek2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adress")
public class AddressesController {
    @Autowired
    AddressesService addressesService;

    @PostMapping("")
    public Address save(@RequestBody @Validated AddressDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
        } else {
            return addressesService.save(body);
        }
    }
//    @PutMapping("")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public Address updateAddress(@AuthenticationPrincipal User admin, long address_id, @RequestBody @Validated AddressDTO body, BindingResult validation) {
//        if (validation.hasErrors()) {
//            throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
//        } else {
//            try {
//                return addressesService.findByIdAndUpdate(admin.getId(), address_id, body);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
