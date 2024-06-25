package mk.ukim.finki.eglas.services;

import jakarta.persistence.ManyToOne;
import mk.ukim.finki.eglas.model.Election;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.ElectoralUnit;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ElectionRealizationService {
    List<ElectionRealization> findAll();
    ElectionRealization findById(Long id);
    ElectionRealization update(Long id,
                               LocalDate date,
                               String name,
                               Long electionId,
                               String candidacyElections,
                               String candidateListElections);
    ElectionRealization delete(Long id);
}
