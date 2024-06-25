package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Address;
import mk.ukim.finki.eglas.model.Party;
import mk.ukim.finki.eglas.repository.PartyRepository;
import mk.ukim.finki.eglas.services.AddressService;
import mk.ukim.finki.eglas.services.PartyService;
import org.springframework.stereotype.Service;

import java.util.List;
// Jovan
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final AddressService addressService;

    PartyServiceImpl(PartyRepository partyRepository, AddressService addressService){
        this.partyRepository = partyRepository;
        this.addressService = addressService;
    }

    @Override
    public List<Party> findAll() {
        return partyRepository.findAll();
    }

    @Override
    public Party findById(Long id) {
        return partyRepository.findById(id).orElseThrow(() -> new RuntimeException("Party not found"));
    }

    @Override
    public Party update(Long id, String name, String street, Integer houseNumber, Integer entranceNumber, Integer apartmentNumber, Long municipality) {
        Address address = addressService.findByStreetLikeAndHouseNumberAndEntranceNumberAndApartmentNumberAAndMunicipality(street, houseNumber, entranceNumber, apartmentNumber, municipality);
        Party party = new Party();
        if(id != null)
        {
            party = findById(id);
        }
        party.setName(name);
        party.setAddress(address);
        return partyRepository.save(party);
    }

    @Override
    public Party delete(Long id) {
        Party party = findById(id);
        partyRepository.delete(party);
        return party;
    }
}
