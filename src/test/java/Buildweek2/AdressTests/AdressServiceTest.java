package Buildweek2.AdressTests;

import Buildweek2.address.Address;
import Buildweek2.address.AddressesService;
import Buildweek2.address.payloads.AddressDTO;
import Buildweek2.authorization.AuthService;
import Buildweek2.client.BusinessName;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
public class AdressServiceTest {

  @Autowired
  AddressesService as;
  @Autowired
  ClientService cs;
  @Autowired
  AuthService ats;
  private Client client;

  private AddressDTO addressDTO;
  private Address address;

  @BeforeEach
  public void setUp() {
    client = ats.save(new NewClientDTO("clientName", "clientSurname", "as@gmail.com", "3287239805", "3287239805"
            , new Date(), LocalDate.now(), "companyName", "11111111111", "as2@gmail.com"
            , 129120291L, "pec@pec.it", "logo", BusinessName.SAS.name()));
    addressDTO = new AddressDTO("street", "civic", "location", "postalCode", 323L, client.getId());
    address = as.save(addressDTO);
  }

  @AfterEach
  public void afterAll() {
    as.findByIdAndDelete(address.getId());
    cs.removeClient(client.getId());
  }

  @Test
  public void testSaveAddressNotNull() {
    Assertions.assertNotNull(address);
  }

  @Test
  public void testSaveAddressReturnNotFoundCLientId() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      addressDTO = new AddressDTO("street", "civic", "location", "postalCode", 323L, 4500L);
      address = as.save(addressDTO);
    });
  }

  @Test
  public void testFindByIdReturnCorrectAddres() {
    Address found = as.findById(address.getId());
    Assertions.assertEquals(address, found);
  }

  @Test
  public void testFindAddressByIdReturnNotFound() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      as.findById(212342L);
    });
  }

  @Test
  public void testFindAddressByClientIdReturnNotFound() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      as.findAddressByClientId(234234);
    });
  }

  @Test
  public void testFindAddressByIdAndUpdateReturnUpdatedAddress() {
    AddressDTO addressDTOUpdated = new AddressDTO("streeeet", "civic", "location", "postalCode", 323L, client.getId());
    Address address1 = as.findByIdAndUpdate(address.getId(), addressDTOUpdated);
    Assertions.assertNotEquals(address, address1);
  }

  @Test
  public void testFindAddressByIdAndDeleteReturnNotFoundAfterFindById() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      as.findAddressByClientId(234234);
    });
  }
}
