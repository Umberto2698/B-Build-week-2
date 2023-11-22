package Buildweek2.client;

import Buildweek2.address.Address;
import Buildweek2.bill.Bill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "ClientBuilder")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "contact_surname")
    private String contactSurname;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "phone")
    private String phone;
    @Column(name = "creation_date")
    private Date insertDate;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "VAT_number")
    private String VATNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "last_contract_date")
    private LocalDate lastContractDate;
    @Column(name = "annual_turnhover")
    private long annualTurnHover;
    @Column(name = "pec")
    private String pec;
    @Column(name = "company_logo_url")
    private String companyLogo;
    @Column(name = "business_name")
    @Enumerated(EnumType.STRING)
    private BusinessName businessName;

    @OneToMany(mappedBy = "client")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Bill> bills = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(contactName, client.contactName) && Objects.equals(contactSurname, client.contactSurname) && Objects.equals(contactEmail, client.contactEmail) && Objects.equals(contactPhone, client.contactPhone) && Objects.equals(phone, client.phone) && Objects.equals(companyName, client.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactName, contactSurname, contactEmail, contactPhone, phone, insertDate, companyName, VATNumber, email, lastContractDate, annualTurnHover, pec, companyLogo, businessName, addresses, bills);
    }

    public static class ClientBuilder {
    }
}
