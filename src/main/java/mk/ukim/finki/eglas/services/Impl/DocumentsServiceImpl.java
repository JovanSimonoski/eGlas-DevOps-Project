package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.Documents;
import mk.ukim.finki.eglas.repository.DocumentsRepository;
import mk.ukim.finki.eglas.services.CitizenService;
import mk.ukim.finki.eglas.services.DocumentsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final CitizenService citizenService;

    public DocumentsServiceImpl(DocumentsRepository documentsRepository, CitizenService citizenService) {
        this.documentsRepository = documentsRepository;
        this.citizenService = citizenService;
    }

    @Override
    public List<Documents> findAll() {
        return documentsRepository.findAll();
    }

    @Override
    public Documents findById(Long id) {
        return documentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @Override
    public Documents update(Long id, String documentNumber, LocalDate issueDate, LocalDate expiryDate, String issuer, Long citizenId) {
        Documents documents = new Documents();
        Citizen citizen = citizenService.findById(citizenId);
        if(id != null)
        {
            documents = findById(id);
        }
        documents.setDocumentNumber(documentNumber);
        documents.setIssuer(issuer);
        documents.setIssueDate(issueDate);
        documents.setExpiryDate(expiryDate);
        documents.setCitizen(citizen);
        return documentsRepository.save(documents);
    }

    @Override
    public Documents delete(Long id) {
        Documents documents = findById(id);
        documentsRepository.delete(documents);
        return documents;
    }
}
