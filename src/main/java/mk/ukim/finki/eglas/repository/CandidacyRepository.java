package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Candidacy;
import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidacyRepository extends JpaRepository<Candidacy, Long> {
    List<Candidacy> findAllByCandidatesElectionRealizationAndMunicipality(CandidatesElectionRealization electionRealization,
                                                                          Municipality municipality);
}
