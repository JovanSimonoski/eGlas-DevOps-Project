package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Address;
import mk.ukim.finki.eglas.model.Municipality;
import mk.ukim.finki.eglas.model.PollingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select distinct a.street from Address a")
    List<String> findAllDistinctStreet();
    Optional<Address> findByStreetLikeAndHouseNumberAndEntranceNumberAndApartmentNumberAndMunicipality_Id(String street,
                                                                                                        Integer houseNumber,
                                                                                                        Integer entranceNumber,
                                                                                                        Integer apartmentNumber,
                                                                                                        Long municipality);
}
