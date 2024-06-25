package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Coalition;

import java.util.List;

public interface CoalitionService {
    Coalition findById(Long id);
    List<Coalition> findAll();
    void addPartyToCoalition(Long coalitionId, Long partyId);
    Coalition update(Long id, String name, String motto, Long electionRealization, List<Long> partiesId);
    void delete(Long id);
}
