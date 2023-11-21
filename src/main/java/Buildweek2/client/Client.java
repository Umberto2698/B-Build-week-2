package Buildweek2.client;

import Buildweek2.address.Address;
import Buildweek2.bill.Bill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @CreationTimestamp
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
    private Set<Bill> bills = new HashSet<>();
}
