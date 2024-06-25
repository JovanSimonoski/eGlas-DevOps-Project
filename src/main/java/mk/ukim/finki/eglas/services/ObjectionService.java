package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Objection;
import mk.ukim.finki.eglas.model.UserProfile;

import java.util.List;

public interface ObjectionService {
    List<Objection> findAll();
    Objection findById(Long id);
    Objection update(Long id, Long electionRealizationId, Long pollingStationId, String description);
    Objection delete(Long id);
    Objection accept(Long id);
    Objection reject(Long id);
}
