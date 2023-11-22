package Buildweek2.address;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class AddressesController {
  @Autowired
  AddressesService addressesService;

//  @PutMapping("")
//  @PreAuthorize("hasAuthority('ADMIN')")
//  public Address updateAddress(@AuthenticationPrincipal User admin, long address_id, @RequestBody @Validated AddressDTO body, BindingResult validation) {
//    if (validation.hasErrors()) {
//      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
//    } else {
//      try {
//        return addressesService.findByIdAndUpdate(admin.getId(), address_id, body);
//      } catch (Exception e) {
//        throw new RuntimeException(e);
//      }
//    }
//  }
}
