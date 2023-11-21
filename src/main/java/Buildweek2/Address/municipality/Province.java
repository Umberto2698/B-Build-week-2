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
@Table(name = "provinces")
public class Province implements CsvFileEntitiy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String provinceAbbreviation;
  private String provinceName;
  private String region;


  @Override
  public void setFirstProperty(String string) {
    setProvinceAbbreviation(string);
  }

  @Override
  public void setSecondProperty(String string) {
    setProvinceName(string);
  }

  @Override
  public <T> void setThirdProperty(T string) {
    setRegion((String) string);
  }
}
