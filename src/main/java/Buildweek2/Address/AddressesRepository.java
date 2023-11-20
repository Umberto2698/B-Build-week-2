package Buildweek2.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressesRepository extends JpaRepository<Address, Long> {
  @Query("Select a from Address a WHERE a.client.id = :clientId")
  Optional<Address> findByClientId(long clientId);
}
