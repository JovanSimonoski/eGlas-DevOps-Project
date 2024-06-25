package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Address;
import mk.ukim.finki.eglas.model.PollingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollingStationRepository extends JpaRepository<PollingStation, Long> {
    List<PollingStation> findAllByAddress_Municipality_Id(Long municipalityId);
    PollingStation findPollingStationByAddress(Address address);
}
