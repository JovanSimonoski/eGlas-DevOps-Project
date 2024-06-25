package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.ElectionRealization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRealizationRepository extends JpaRepository<ElectionRealization, Long> {
}
