package Buildweek2.Address.municipality;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "provinces")
public class Province implements CsvFileEntitiy {
  @Id
  private String provinceAbbreviation;
  private String provinceName;
  private String region;

  @Override
  public void setFirstProperty(String string) {
    this.provinceAbbreviation = string;
  }

  @Override
  public void setSecondProperty(String string) {
    this.provinceName = string;
  }

  @Override
  public void setThirdProperty(String string) {
    this.region = string;
  }
}
