package Buildweek2.bill;

import Buildweek2.client.Client;
import Buildweek2.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private long amount;
    private long number;
    @Enumerated(EnumType.STRING)
    private BillState state;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
