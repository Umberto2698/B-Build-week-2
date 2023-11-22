package Buildweek2.address;


import Buildweek2.address.municipality.MunicipalitiesService;
import Buildweek2.address.municipality.Municipality;
import Buildweek2.address.payloads.AddressDTO;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressesService {
  @Autowired
  AddressesRepository addressRepository;

  @Autowired
  MunicipalitiesService municipalitiesService;

  @Autowired
  ClientService clientService;


  public Address save(AddressDTO body) {
    Municipality municipality = municipalitiesService.findById(body.municipallyId());
    Client client = clientService.getSingleClient(body.clientId());
    Address address = new Address();
    address.setCivic(body.civic());
    address.setMunicipality(municipality);
    address.setLocation(body.location());
    address.setPostalCode(body.postalCode());
    address.setStreet(body.street());
    address.setClient(client);
    return addressRepository.save(address);
  }

  public Address findById(Long id) {
    return addressRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Address findByIdAndUpdate(long address_id, AddressDTO body) {
    Municipality municipality = municipalitiesService.findById(body.municipallyId());
    Client client = clientService.getSingleClient(body.clientId());
    Address addressUpdated = this.findById(address_id);
    addressUpdated.setStreet(body.street());
    addressUpdated.setCivic(body.civic());
    addressUpdated.setLocation(body.location());
    addressUpdated.setMunicipality(municipality);
    addressUpdated.setClient(client);
    return addressRepository.save(addressUpdated);
  }

  public Address findAddressByClientId(long client_id) {
    return addressRepository.findByClientId(client_id).orElseThrow(() -> new NotFoundException(client_id));
  }

  public void findByIdAndDelete(long address_id) {
    addressRepository.deleteById(address_id);
  }
}
