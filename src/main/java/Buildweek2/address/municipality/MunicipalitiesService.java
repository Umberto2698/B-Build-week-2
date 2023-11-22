package Buildweek2.address.municipality;

import Buildweek2.address.Province.Province;
import Buildweek2.address.Province.ProvincesService;
import Buildweek2.exceptions.NotFoundException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class MunicipalitiesService {
    @Autowired
    MunicipalitiesRepository mr;
    @Autowired
    ProvincesService pr;

    public void saveMunicapality(Municipality entitiy) {
        mr.save(entitiy);
    }

    public Municipality findById(long id) {
        return mr.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void readCsvFileMunicipality(String path) throws IOException {

        if (mr.findAll().isEmpty()) {
            try {
                CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
                CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
                List<String[]> municipalitiesRows = reader.readAll();

                for (int i = 0; i < municipalitiesRows.size() - 1; i++) {
                    Municipality entity = this.createMunicipality(municipalitiesRows.get(i));
                    this.saveMunicapality(entity);
                }

            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Municipality createMunicipality(String[] line) {
        try {
            Municipality municipality = new Municipality();
            municipality.setDenomination(line[2]);
            Province province = pr.getProvinceByName(line[3]);
            municipality.setProvince(province);
            return municipality;
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity", e);
        }

    }
}
