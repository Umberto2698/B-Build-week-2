package Buildweek2.Address.municipality;

import Buildweek2.Address.Province.Province;
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
