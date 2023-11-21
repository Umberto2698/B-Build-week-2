package Buildweek2.address.Province;

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

    public List<Province> getAllProvinces() {
        return pr.findAll();
    }

    public void readProvinceFileCsv(String path) throws IOException {
        if (pr.findAll().isEmpty()) {
            try {
                CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
                CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
                List<String[]> provinceRows = reader.readAll();
                List<String> listStringOld = List.of("Carbonia Iglesias", "Medio Campidano", "Vibo-Valentia", "Ascoli-Piceno", "Reggio-Calabria", "Ogliastra", "Olbia Tempio", "Verbania", "Aosta", "Monza-Brianza", "Bolzano", "Forli-Cesena", "La-Spezia", "Reggio-Emilia", "Pesaro-Urbino");
                List<String[]> listStringNew = new ArrayList<>();
                listStringNew.add(new String[]{"SU", "Sud Sardegna", "Sardegna"});
                listStringNew.add(new String[]{"VCO", "Verbano-Cusio-Ossola", "Piemonte"});
                listStringNew.add(new String[]{"AO", "Valle d'Aosta/Vallée d'Aoste", "Valle d'Aosta"});
                listStringNew.add(new String[]{"BZ", "Bolzano/Bozen", "Trentino Alto Adige"});
                listStringNew.add(new String[]{"MB", "Monza e della Brianza", "Lombardia"});
                listStringNew.add(new String[]{"FC", "Forlì-Cesena", "Emilia Romagna"});
                listStringNew.add(new String[]{"SP", "La Spezia", "Liguria"});
                listStringNew.add(new String[]{"RE", "Reggio nell'Emilia", "Emilia Romagna"});
                listStringNew.add(new String[]{"PU", "Pesaro e Urbino", "Marche"});
                listStringNew.add(new String[]{"AP", "Ascoli Piceno", "Marche"});
                listStringNew.add(new String[]{"RC", "Reggio Calabria", "Calabria"});
                listStringNew.add(new String[]{"VV", "Vibo Valentia", "Calabria"});
                provinceRows.addAll(listStringNew);
                List<String[]> last = provinceRows.stream().filter(array -> !listStringOld.contains(array[1])).toList();
                for (int i = 0; i < last.size(); i++) {
                    Province province = this.createProvince(last.get(i));
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
