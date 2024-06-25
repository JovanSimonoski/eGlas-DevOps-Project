package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
