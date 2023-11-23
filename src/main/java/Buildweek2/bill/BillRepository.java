package Buildweek2.bill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
  Page<Bill> findByClientId(Long clientId, Pageable pageable);

  List<Bill> findByClientId(Long clientId);

  List<Bill> findByState(BillState state);

  @Query("SELECT b FROM Bill b WHERE DATE(b.date) BETWEEN DATE(:startDate) AND DATE(:endDate)")
  Optional<List<Bill>> findByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query("SELECT b FROM Bill b WHERE EXTRACT(YEAR FROM b.date)= :year")
  Optional<List<Bill>> findByDateYear(@Param("year") int year);

  @Query("SELECT b FROM Bill b WHERE b.amount BETWEEN :minAmount AND :maxAmount")
  Optional<List<Bill>> findByRangeAmount(@Param("minAmount") Long minAmount, @Param("maxAmount") Long maxAmount);
}
