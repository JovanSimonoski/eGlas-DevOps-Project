package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
