package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Candidacy;
import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.repository.CandidacyRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidacyServiceImpl implements CandidacyService {

    private final CandidacyRepository candidacyRepository;
    private final CandidateService candidateService;
    private final PartyService partyService;
    private final CandidatesElectionRealizationService candidatesElectionRealizationService;
    private final MunicipalityService municipalityService;
    @Autowired
    private CitizenService citizenService;

    public CandidacyServiceImpl(CandidacyRepository candidacyRepository,
                                CandidateService candidateService,
                                PartyService partyService,
                                CandidatesElectionRealizationService candidatesElectionRealizationService,
                                MunicipalityService municipalityService) {
        this.candidacyRepository = candidacyRepository;
        this.candidateService = candidateService;
        this.partyService = partyService;
        this.candidatesElectionRealizationService = candidatesElectionRealizationService;
        this.municipalityService = municipalityService;
    }


    @Override
    public Candidacy findById(Long id) {
        return candidacyRepository.findById(id).orElseThrow(() -> new RuntimeException("No candidacy found"));
    }

    @Override
    public List<Candidacy> findAll() {
        return candidacyRepository.findAll();
    }

    @Override
    public Candidacy update(Long id, String description, Long candidateId, Long partyId, Long candidatesElectionRealizationId, Long municipalityId) {
        Candidacy candidacy = new Candidacy();
        if (id != null){
            candidacy = findById(id);
        }
        candidacy.setMunicipality(municipalityService.findById(municipalityId));
        candidacy.setDescription(description);
        candidacy.setCandidate(candidateService.findById(candidateId));
        candidacy.setParty(partyService.findById(partyId));
        candidacy.setCandidatesElectionRealization(candidatesElectionRealizationService.findById(candidatesElectionRealizationId));
        return candidacyRepository.save(candidacy);
    }

    @Override
    public void delete(Long id) {
        candidacyRepository.delete(findById(id));
    }

    @Override
    public List<Candidacy> findAllByElectionsRealiztion(Long realizationId, Long citizenId)
    {
        Citizen citizen = citizenService.findById(citizenId);
        CandidatesElectionRealization candidatesElectionRealization = candidatesElectionRealizationService.findById(realizationId);
        return candidacyRepository.findAllByCandidatesElectionRealizationAndMunicipality(candidatesElectionRealization, citizen.getAddress().getMunicipality());
    }
}
