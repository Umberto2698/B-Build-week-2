package Buildweek2.address.Province;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByProvinceName(String s);

}
