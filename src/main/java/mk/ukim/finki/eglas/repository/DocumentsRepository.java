package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {
}
