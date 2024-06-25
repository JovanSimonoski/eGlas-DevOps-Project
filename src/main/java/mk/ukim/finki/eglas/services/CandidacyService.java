package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Candidacy;

import java.util.List;

public interface CandidacyService {
    Candidacy findById(Long id);
    List<Candidacy> findAll();
    Candidacy update(Long id, String description, Long candidateId, Long partyId, Long candidatesElectionRealizationId, Long municipalityId);
    void delete(Long id);
    List<Candidacy> findAllByElectionsRealiztion(Long realizationId, Long citizenId);
}
