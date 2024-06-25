package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.ElectoralUnit;

import java.util.List;

public interface ElectoralUnitService {
    List<ElectoralUnit> findAll();
    ElectoralUnit findById(Long id);
    ElectoralUnit update(Long id, String name, Integer numDeputies);
}
