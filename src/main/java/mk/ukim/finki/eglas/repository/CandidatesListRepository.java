package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.CandidatesList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatesListRepository extends JpaRepository<CandidatesList, Long> {
}
