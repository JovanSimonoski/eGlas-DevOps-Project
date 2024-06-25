package mk.ukim.finki.eglas.services.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import mk.ukim.finki.eglas.model.Address;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.repository.CitizenRepository;
import mk.ukim.finki.eglas.services.AddressService;
import mk.ukim.finki.eglas.services.CitizenService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {
    private final CitizenRepository citizenRepository;
    private final AddressService addressService;
    public CitizenServiceImpl(CitizenRepository citizenRepository, AddressService addressService) {
        this.citizenRepository = citizenRepository;
        this.addressService = addressService;
    }

    @Override
    public List<Citizen> findAll() {
        HashMap<String, Integer> mapa = new HashMap<>();
        return citizenRepository.findAll();
    }

    @Override
    public Citizen findById(Long id) {
        return citizenRepository.findById(id).orElseThrow(() -> new RuntimeException("Citizen not found"));
    }

    @Override
    public Citizen update(Long id, String name, String surname, LocalDate dateOfBirth, String idNum, String street, Integer houseNumber, Integer entranceNumber, Integer apartmentNumber, Long municipality) {
        Address address = addressService.findByStreetLikeAndHouseNumberAndEntranceNumberAndApartmentNumberAAndMunicipality(street, houseNumber, entranceNumber, apartmentNumber, municipality);
        Citizen citizen = new Citizen();
        if(id != null)
        {
            citizen = findById(id);
        }
        citizen.setAddress(address);
        citizen.setName(name);
        citizen.setSurname(surname);
        citizen.setDateOfBirth(dateOfBirth);
        citizen.setIdNum(idNum);
        return citizenRepository.save(citizen);
    }

    @Override
    public Citizen delete(Long id) {
        Citizen citizen = findById(id);
        citizenRepository.save(citizen);
        return citizen;
    }

    @Override
    public Citizen findByIdNum(String idNum) {
//        Optional<Citizen> orel = citizenRepository.findByIdNum(idNum);
        return citizenRepository.findByIdNum(idNum).orElseThrow(() -> new RuntimeException("Citizen with given id number not found"));
    }

    @Override
    public Citizen validateCitizen(String idNum, String documentNumber)
    {
        try {
            Citizen citizen = findByIdNum(idNum);
            String ds = citizen.getDocument().getDocumentNumber();
            Boolean orel = citizen.getDocument().getDocumentNumber().equals(documentNumber);
            return citizen.getDocument().getDocumentNumber().equals(documentNumber) ? citizen : null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    @Override
    public Integer availableVoters() {
        return citizenRepository.availableVoters();
    }

    @Override
    public List<Citizen> findAllByAddress_PollingStation_Id(Long pollingStationId) {
        return citizenRepository.findAllByDocumentIsNotNullAndAddress_PollingStation_Id(pollingStationId);
    }
}
