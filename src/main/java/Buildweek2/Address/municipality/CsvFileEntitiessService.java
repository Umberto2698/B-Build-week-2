package Buildweek2.Address.municipality;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class CsvFileEntitiessService {
  @Autowired
  CsvFileInterfaceRepository cfr;

  public void save(CsvFileEntitiy entitiy) {
    cfr.save(entitiy);
  }

  public void readCsvFileAndAddToDatabase(String path, CsvFileEntitiy entityType) throws IOException {
    List<CsvFileEntitiy> entityList = new ArrayList<>();
    try {
      CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
      CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).build();
      List<String[]> entitysRows = reader.readAll();

      for (String[] row : entitysRows) {
        CsvFileEntitiy entity = this.createEntity(row, entityType);
        cfr.save(entity);
      }

    } catch (CsvException e) {
      throw new RuntimeException(e);
    }
  }

  private CsvFileEntitiy createEntity(String[] line, CsvFileEntitiy entityToCreate) {
    try {
      Constructor<? extends CsvFileEntitiy> constructor = entityToCreate.getClass().getConstructor();
      CsvFileEntitiy entity = constructor.newInstance();
      entity.setFirstProperty(line[0]);
      entity.setSecondProperty(line[1]);
      entity.setThirdProperty(line[2]);

      return entity;

    } catch (Exception e) {
      throw new RuntimeException("Error creating entity", e);
    }

  }
}
