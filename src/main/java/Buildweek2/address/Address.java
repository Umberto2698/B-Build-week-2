package Buildweek2.address;

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
    private Long postalCode;
    //  @OneToOne
//  @JoinColumn(name = "municipality_id")
    private Long municipality;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
