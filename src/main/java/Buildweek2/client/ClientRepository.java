package Buildweek2.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    @Query("SELECT c FROM Client c WHERE c.annualTurnHover = :annualTurnHover")
    Optional<List<Client>> filterByTurnHover(@Param("annualTurnHover") long annualTurnHover);
    @Query("SELECT c FROM Client c WHERE DATE(c.insertDate) BETWEEN DATE(:startDate) AND DATE(:endDate)")
    Optional<List<Client>> filterByInsertDate(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT c FROM Client c WHERE DATE(c.lastContractDate) BETWEEN DATE(:startDate) AND DATE(:endDate)")
    Optional<List<Client>> filterByLastContractDate(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT c FROM Client c WHERE c.CompanyName LIKE %:partialName%")
    Optional<List<Client>> filterByPartialName(@Param("partialName") String partialToSearch);
}
