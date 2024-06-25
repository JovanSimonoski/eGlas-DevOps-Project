package mk.ukim.finki.eglas.services.Impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.repository.CandidatesListRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CandidatesListServiceImpl implements CandidatesListService {
    private final CandidatesListRepository repository;
    private final CandidatesListElectionRealizationService candidatesListElectionRealizationService;
    private final CandidateService candidateService;
    private final ElectoralUnitService electoralUnitService;
    private final MunicipalityService municipalityService;
    private final PartyService partyService;

    public CandidatesListServiceImpl(CandidatesListRepository repository,
                                     CandidatesListElectionRealizationService candidatesListElectionRealizationService,
                                     CandidateService candidateService, ElectoralUnitService electoralUnitService, MunicipalityService municipalityService, PartyService partyService){
        this.repository = repository;
        this.candidatesListElectionRealizationService = candidatesListElectionRealizationService;
        this.candidateService = candidateService;
        this.electoralUnitService = electoralUnitService;
        this.municipalityService = municipalityService;
        this.partyService = partyService;
    }

    @Override
    public CandidatesList findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No candidates list found"));
    }

    @Override
    public List<CandidatesList> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CandidatesList update(Long id, String description, Long partyId, Long candidatesListElectionRealizationId, Long municipalityId, Long electoralUnitId, List<Long> candidatesInList) {
        if(candidatesInList.size() != 20)
        {
            throw new RuntimeException("Candidates list must have 20 candidates!");
        }
        
        CandidatesList list;

        ElectoralUnit electoralUnit = electoralUnitId != null ? electoralUnitService.findById(electoralUnitId) : null;
        Municipality municipality = municipalityId != null ? municipalityService.findById(municipalityId) : null;
        CandidatesListElectionRealization candidatesListElectionRealization = candidatesListElectionRealizationService.findById(candidatesListElectionRealizationId);
        Party party = partyService.findById(partyId);

        if (id != null) {
            list = findById(id);
            list.getCandidates().clear();
            repository.save(list);
        } else {
            throw new IllegalArgumentException("Id must be provided for update operation.");
        }

        candidatesInList.stream()
                .filter(Objects::nonNull)
                .map(candidateService::findById)
                .forEach(x -> list.candidates.add(x));
        list.setDescription(description);
        list.setParty(party);
        list.setMunicipality(municipality);
        list.setElectoralUnit(electoralUnit);
        list.setCandidatesListElectionRealization(candidatesListElectionRealization);
        return repository.save(list);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public void addCandidateToCandidatesList(Long candidatesListId, Long candidateId) {
        CandidatesList list = findById(candidatesListId);
        list.getCandidates().add(candidateService.findById(candidateId));
    }
}
