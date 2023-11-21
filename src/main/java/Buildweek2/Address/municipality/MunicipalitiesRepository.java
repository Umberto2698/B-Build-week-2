package Buildweek2.Address.municipality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalitiesRepository extends JpaRepository<Municipality, String> {
}
