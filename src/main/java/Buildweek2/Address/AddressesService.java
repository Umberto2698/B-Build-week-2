package Buildweek2.Address;

import Buildweek2.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressesService {
  @Autowired
  AddressesRepository addressRepository;

  public Address save(Address address) {
    return addressRepository.save(address);
  }

  public Address findById(Long id) {
    return addressRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  public Address findByIdAndUpdate(long client_id, long address_id, AddressDTO addressDTO) {
    Address addressUpdated = this.findAddressByClientId(address_id);
    addressUpdated.setStreet(addressDTO.street());
    addressUpdated.setCivic(addressDTO.civic());
    addressUpdated.setLocation(addressDTO.location());
    addressUpdated.setMunicipality(addressDTO.municipally());
    return addressRepository.save(addressUpdated);
  }

  private Address findAddressByClientId(long client_id) {
    return addressRepository.findByClientId(client_id).orElseThrow(() -> new ItemNotFoundException(client_id));
  }

  public void findByIdAndDelete(long address_id) {
    addressRepository.deleteById(address_id);
  }
}
