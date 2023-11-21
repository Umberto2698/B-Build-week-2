package Buildweek2.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    @Query("SELECT c FROM Client c WHERE c.annualTurnHover = :annualTurnHover")
    public List<Client> filterByTurnHover(@Param("annualTurnHover") long annualTurnHover);
}
