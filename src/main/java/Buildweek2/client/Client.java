package Buildweek2.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contactName;
    private String contactSurname;
    private String contactEmail;
    private long contactPhone;
    private long phone;
    private LocalDate insertDate;
    private String companyName;
    private String VATNumber;
    private String email;
    private LocalDate lastContractDate;
    private long annualTurnHover;
    private String pec;
    private String companyLogo;
    private BusinessName businessName;
}
