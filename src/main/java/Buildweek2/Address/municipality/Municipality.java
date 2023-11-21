package Buildweek2.Address.municipality;

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
public class Municipality implements CsvFileEntitiy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String denomination;
  @ManyToOne
  @JoinColumn(name = "province_id")
  private Province province;

  @Override
  public void setFirstProperty(String string) {

  }

  @Override
  public void setSecondProperty(String string) {
    setDenomination(string);
  }

  @Override
  public <T> void setThirdProperty(T element) {
    setProvince((Province) element);
  }
}
