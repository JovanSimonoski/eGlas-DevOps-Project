package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Citizen;

import java.time.LocalDate;
import java.util.List;

public interface CitizenService {
    List<Citizen> findAll();
    Citizen findById(Long id);
    Citizen update(Long id, String name, String surname, LocalDate dateOfBirth, String idNum, String street, Integer houseNumber, Integer entranceNumber, Integer apartmentNumber, Long municipality);
    Citizen delete(Long id);
    Citizen findByIdNum(String idNum);
    Citizen validateCitizen(String idNum, String documentNumber);
    Integer availableVoters();
    List<Citizen> findAllByAddress_PollingStation_Id(Long pollingStationId);
}
