package Buildweek2.address;

import Buildweek2.address.municipality.Municipality;
import Buildweek2.client.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "addressess")
@ToString
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String street;
  private String civic;
  private String location;
  private String postalCode;
  @ManyToOne
  @JoinColumn(name = "municipality_id")
  private Municipality municipality;
  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonIgnore
  private Client client;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Address address = (Address) object;
    return id == address.id && Objects.equals(street, address.street) && Objects.equals(civic, address.civic) && Objects.equals(location, address.location) && Objects.equals(postalCode, address.postalCode) && Objects.equals(municipality.getId(), address.municipality.getId()) && Objects.equals(client.getId(), address.client.getId());
  }
}
