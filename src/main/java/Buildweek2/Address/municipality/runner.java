package Buildweek2.Address.municipality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class runner implements CommandLineRunner {
  @Autowired
  CsvFileEntitiessService ces;

  @Override
  public void run(String... args) throws Exception {
    String provincesUrl = "src/main/java/Buildweek2/Address/Province/Province.java";
    String municipalityUrl = "src/main/java/Buildweek2/comuni-italiani.csv";
    ces.readCsvFileAndAddToDatabase(provincesUrl, new Province());
    ces.readCsvFileAndAddToDatabase(municipalityUrl, new Municipality());
  }
}
