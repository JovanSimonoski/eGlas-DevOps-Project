package mk.ukim.finki.eglas.services;

import jakarta.persistence.OneToOne;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.Documents;

import java.time.LocalDate;
import java.util.List;

public interface DocumentsService {
    List<Documents> findAll();
    Documents findById(Long id);
    Documents update(Long id, String documentNumber, LocalDate issueDate, LocalDate expiryDate, String issuer, Long citizenId);
    Documents delete(Long id);
}
