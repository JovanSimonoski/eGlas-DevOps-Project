package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.ElectoralUnit;
import mk.ukim.finki.eglas.repository.ElectoralUnitRepository;
import mk.ukim.finki.eglas.services.ElectoralUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectoralUnitServiceImpl implements ElectoralUnitService {
    @Autowired
    ElectoralUnitRepository electoralUnitRepository;
    @Override
    public List<ElectoralUnit> findAll() {
        return electoralUnitRepository.findAll();
    }

    @Override
    public ElectoralUnit findById(Long id) {
        return electoralUnitRepository.findById(id).orElseThrow(() -> new RuntimeException("Electoral unit not found"));
    }

    @Override
    public ElectoralUnit update(Long id, String name, Integer numDeputies) {
        ElectoralUnit electoralUnit = new ElectoralUnit();
        if(id != null)
        {
            electoralUnit = findById(id);
        }
        electoralUnit.setName(name);
        electoralUnit.setNumDeputies(numDeputies);
        return electoralUnitRepository.save(electoralUnit);
    }
}
