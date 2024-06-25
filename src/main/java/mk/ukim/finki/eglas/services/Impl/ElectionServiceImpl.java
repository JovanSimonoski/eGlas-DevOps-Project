package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Election;
import mk.ukim.finki.eglas.repository.ElectionRepository;
import mk.ukim.finki.eglas.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {
    @Autowired
    ElectionRepository electionRepository;

    @Override
    public List<Election> findAll() {
        return electionRepository.findAll();
    }

    @Override
    public Election findById(Long id) {
        return electionRepository.findById(id).orElseThrow(() -> new RuntimeException("Election not found"));
    }

    @Override
    public Election update(Long id, String name) {
        Election election = new Election();
        if(id != null)
        {
            election = findById(id);
        }
        election.setName(name);
        return electionRepository.save(election);
    }

    @Override
    public Election delete(Long id) {
        Election election = findById(id);
        electionRepository.delete(election);
        return election;
    }
}
