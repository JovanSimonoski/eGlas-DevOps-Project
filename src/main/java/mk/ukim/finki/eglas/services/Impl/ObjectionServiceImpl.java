package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.repository.ObjectionRepository;
import mk.ukim.finki.eglas.repository.PartyRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.stereotype.Service;

import java.util.List;
// Jovan

@Service
public class ObjectionServiceImpl implements ObjectionService {
    private final ObjectionRepository objectionRepository;
    private final PartyService partyService;
    private final ElectionRealizationService electionRealizationService;
    private final AddressService addressService;

    ObjectionServiceImpl(ObjectionRepository objectionRepository, PartyService partyService, ElectionRealizationService electionRealizationService, AddressService addressService){
        this.objectionRepository = objectionRepository;
        this.partyService = partyService;
        this.electionRealizationService = electionRealizationService;
        this.addressService = addressService;
    }

    @Override
    public List<Objection> findAll() {
        return objectionRepository.findAll();
    }

    @Override
    public Objection findById(Long id) {
        return objectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Objection not found"));
    }

    @Override
    public Objection update(Long id, Long electionRealizationId, Long pollingStationId, String description) {
        ElectionRealization electionRealization = electionRealizationService.findById(electionRealizationId);
        PollingStation pollingStation = addressService.findPollingStationById(pollingStationId);
        Objection objection = new Objection();
        if(id != null)
        {
            objection = findById(id);
        }

        objection.setDescription(description);
        objection.setElectionRealization(electionRealization);
        objection.setPollingStation(pollingStation);
        return objectionRepository.save(objection);
    }

    @Override
    public Objection delete(Long id) {
        Objection objection = findById(id);
        objectionRepository.delete(objection);
        return objection;
    }

    @Override
    public Objection accept(Long id) {
        Objection objection = findById(id);
        objection.setStatus(2);
        return objectionRepository.save(objection);
    }

    @Override
    public Objection reject(Long id) {
        Objection objection = findById(id);
        objection.setStatus(3);
        return objectionRepository.save(objection);
    }
}
