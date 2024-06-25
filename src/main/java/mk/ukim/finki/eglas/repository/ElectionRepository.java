package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
