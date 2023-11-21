package Buildweek2.address;

import Buildweek2.address.municipality.Municipality;
import Buildweek2.client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "addressess")
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
    private Client client;
}
