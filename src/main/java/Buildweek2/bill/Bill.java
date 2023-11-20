package Buildweek2.bill;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private long amount;
    private int number;
    @Enumerated(EnumType.STRING)
    private BillState state;
    /*@ManytoOne
    @JoinColumn(name = "cliente_id")
    private Client client;*/

    /*@ManytoOne
     @JoinColumn(name = "user_id")
    private User user;*/
}
