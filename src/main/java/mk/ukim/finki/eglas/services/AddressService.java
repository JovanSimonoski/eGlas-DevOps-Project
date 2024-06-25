package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Address;
import mk.ukim.finki.eglas.model.PollingStation;

import java.util.List;

public interface AddressService {

    List<Address> findAllAddresses();
    Address findAddressById(Long id);
    Address updateAddress(Long id,
                   String street,
                   Integer houseNumber,
                   Integer entranceNumber,
                   Integer apartmentNumber,
                   Long municipality,
                   Long pollingStation);
    void deleteAddress(Long id);

    List<PollingStation> findAllPollingStations();
    List<PollingStation> findAllPollingStationsByMunicipalityId(Long municipalityId);
    PollingStation findPollingStationById(Long id);
    PollingStation updatePollingStation(Long id, String name, String street, Integer houseNumber, Integer entranceNumber, Integer apartmentNumber, Long municipality, Long electoralUnitId);
    PollingStation deletePollingStation(Long id);
    PollingStation findPollingStationByCitizenId(Long citizenId);
    List<String> findStreetNames();
    Address findByStreetLikeAndHouseNumberAndEntranceNumberAndApartmentNumberAAndMunicipality(String street,
                                                                                              Integer houseNumber,
                                                                                              Integer entranceNumber,
                                                                                              Integer apartmentNumber,
                                                                                              Long municipality);
}
