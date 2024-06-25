package mk.ukim.finki.eglas.services.Impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.Coalition;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.repository.CoalitionRepository;
import mk.ukim.finki.eglas.services.CoalitionService;
import mk.ukim.finki.eglas.services.ElectionRealizationService;
import mk.ukim.finki.eglas.services.PartyService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoalitionServiceImpl implements CoalitionService {

    private final CoalitionRepository coalitionRepository;
    private final ElectionRealizationService electionRealizationService;
    private final PartyService partyService;

    CoalitionServiceImpl(CoalitionRepository coalitionRepository,
                         ElectionRealizationService electionRealizationService,
                         PartyService partyService){
        this.coalitionRepository = coalitionRepository;
        this.electionRealizationService = electionRealizationService;
        this.partyService = partyService;
    }


    @Override
    public Coalition findById(Long id) {
        return coalitionRepository.findById(id).orElseThrow(() -> new RuntimeException("No coalition found"));
    }

    @Override
    public List<Coalition> findAll() {
        return coalitionRepository.findAll();
    }

    @Override
    public void addPartyToCoalition(Long coalitionId, Long partyId) {
        Coalition coalition = findById(coalitionId);
        coalition.getParties().add(partyService.findById(partyId));
        coalitionRepository.save(coalition);
    }

    @Transactional
    @Override
    public Coalition update(Long id, String name, String motto, Long electionRealization, List<Long> partiesId) {
        Coalition coalition;
        if (id != null){
            coalition = findById(id);
        }else {
            coalition = new Coalition();
        }
        coalition.setName(name);
        coalition.setMotto(motto);
        coalition.setElectionRealization(electionRealizationService.findById(electionRealization));
        coalitionRepository.save(coalition);
        partiesId.forEach(partyId -> addPartyToCoalition(coalition.getId(), partyId));
        return coalition;
    }

    @Override
    public void delete(Long id) {
        coalitionRepository.delete(findById(id));
    }
}
