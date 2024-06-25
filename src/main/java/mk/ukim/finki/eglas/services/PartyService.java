package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Party;

import java.util.List;

public interface PartyService {
    List<Party> findAll();
    Party findById(Long id);
    Party update(Long id, String name, String street, Integer houseNumber, Integer entranceNumber, Integer apartmentNumber, Long addressId);
    Party delete(Long id);
}
