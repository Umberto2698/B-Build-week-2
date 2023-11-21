package Buildweek2.runners;

import Buildweek2.Address.Province.ProvincesService;
import Buildweek2.Address.municipality.MunicipalitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AddMunicipality implements CommandLineRunner {
    @Autowired
    private MunicipalitiesService ms;
    @Autowired
    private ProvincesService ps;

    @Override
    public void run(String... args) throws Exception {
        String provincesUrl = "src/main/java/Buildweek2/province-italiane.csv";
        String municipalityUrl = "src/main/java/Buildweek2/comuni-italiani.csv";

        ps.readProvinceFileCsv(provincesUrl);
        ms.readCsvFileMunicipality(municipalityUrl);
    }
}
