package Buildweek2.Address;

import Buildweek2.exceptions.BadRequestException;
import Buildweek2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/adress")
public class AddressesController {
  @Autowired
  AddressesService addressesService;

  @PutMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ADMIN')")
  public Address addEvent(@AuthenticationPrincipal User admin, long address_id, @RequestBody @Validated AddressDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return addressesService.findByIdAndUpdate(admin.getId(), address_id, body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
