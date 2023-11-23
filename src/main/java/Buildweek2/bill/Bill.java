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
import java.util.Objects;


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
  private Client client;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Bill bill = (Bill) object;
    return id == bill.id && amount == bill.amount && number == bill.number && Objects.equals(date, bill.date) && state == bill.state && Objects.equals(client.getId(), bill.client.getId()) && Objects.equals(user.getId(), bill.user.getId());
  }

  @Override
  public String toString() {
    return "Bill{" +
            "id=" + id +
            ", date=" + date +
            ", amount=" + amount +
            ", state=" + state +
            '}';
  }
}
