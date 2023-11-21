package Buildweek2.address.municipality;

import Buildweek2.address.Province.Province;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denomination;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;


}
