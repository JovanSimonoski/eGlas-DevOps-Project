package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Election;
import mk.ukim.finki.eglas.model.Municipality;
import mk.ukim.finki.eglas.repository.ElectionRepository;
import mk.ukim.finki.eglas.repository.MunicipalityRepository;
import mk.ukim.finki.eglas.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// Jovan
@Service
public class MunicipalityServiceImpl implements MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    MunicipalityServiceImpl(MunicipalityRepository municipalityRepository){
        this.municipalityRepository = municipalityRepository;
    }

    @Override
    public List<Municipality> findAll() {
        return municipalityRepository.findAll();
    }

    @Override
    public Municipality findById(Long id) {
        return municipalityRepository.findById(id).orElseThrow(() -> new RuntimeException("Municipality not found"));
    }

    @Override
    public Municipality update(Long id, String name) {
        Municipality municipality = new Municipality();
        if(id != null)
        {
            municipality = findById(id);
        }
        municipality.setName(name);
        return municipalityRepository.save(municipality);
    }

    @Override
    public Municipality delete(Long id) {
        Municipality municipality = findById(id);
        municipalityRepository.delete(municipality);
        return municipality;
    }
}
