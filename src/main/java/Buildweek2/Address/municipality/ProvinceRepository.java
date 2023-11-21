package Buildweek2.Address.municipality;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
  Province getProvinceByProvinceName(String provinceName);
}
