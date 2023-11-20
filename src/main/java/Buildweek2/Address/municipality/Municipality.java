package Buildweek2.Address.municipality;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
  private String id;
  private String denomination;
  private String idProvince;

  @Override
  public void setFirstProperty(String string) {
    this.idProvince = string;
  }

  @Override
  public void setSecondProperty(String string) {
    this.id = string;
  }

  @Override
  public void setThirdProperty(String string) {
    this.denomination = string;
  }
}
