package Buildweek2.Address.Province;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvincesService {
  @Autowired
  ProvinceRepository pr;

  public void save(Province province) {
    pr.save(province);
  }

  public Province getProvinceByName(String s) {
    return pr.findByProvinceName(s);
  }

  public void readProvinceFileCsv(String path) throws IOException {

    if (pr.findAll().isEmpty()) {
      try {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> provinceRows = reader.readAll();
        Boolean wasSaved = false;
        List<String> listStringOld = List.of("Carbonia Iglesias", "Medio Campidano", "Ogliastra", "Olbia Tempio");
        List<String[]> listStringNew = new ArrayList<>();
        listStringNew.add(new String[]{"SU", "Sud Sardegna", "Sardegna"});

        for (int i = 0; i < provinceRows.size() - 1; i++) {

          if (listStringOld.contains(provinceRows.get(i)[1])) {
            provinceRows.remove(i);
          }
          Province province = this.createProvince(provinceRows.get(i));
          this.save(province);
        }

      } catch (CsvException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Province createProvince(String[] line) {
    try {
      Province province = new Province();
      province.setProvinceAbbreviation(line[0]);
      province.setProvinceName(line[1]);
      province.setRegion(line[2]);
      return province;
    } catch (Exception e) {
      throw new RuntimeException("Error creating province", e);
    }

  }

}
