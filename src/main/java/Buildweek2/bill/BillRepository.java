package Buildweek2.bill;

import Buildweek2.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository  extends JpaRepository<Bill, Long> {
    Optional<List<Bill>> findByClientId(Long clientId);
    Optional<List<Bill>> findByState(BillState state);
    @Query("SELECT b FROM Bill b WHERE DATE(b.date) BETWEEN DATE(:startDate) AND DATE(:endDate)")
    Optional<List<Bill>> findByDate(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
    Optional<List<Bill>> findByDateYear(int year);
    @Query("SELECT b FROM Bill b WHERE b.amount BETWEEN :minAmount AND :maxAmount")
    Optional<List<Bill>> findByRangeAmount(@Param("minAmount") Long minAmount,@Param("maxAmount") Long maxAmount);
}
