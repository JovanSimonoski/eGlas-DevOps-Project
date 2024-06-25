package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Election;

import java.util.List;

public interface ElectionService {
    List<Election> findAll();
    Election findById(Long id);
    Election update(Long id, String name);
    Election delete(Long id);
}
