package Buildweek2.address.Province;

import Buildweek2.address.municipality.Municipality;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sigla")
    private String provinceAbbreviation;
    @Column(name = "name")
    private String provinceName;
    @Column(name = "region")
    private String region;
    @OneToMany(mappedBy = "province", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Municipality> municipalityList = new ArrayList<>();
}
