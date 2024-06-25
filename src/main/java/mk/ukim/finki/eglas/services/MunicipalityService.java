package mk.ukim.finki.eglas.services;
import mk.ukim.finki.eglas.model.Municipality;
import java.util.List;

public interface MunicipalityService {
    List<Municipality> findAll();
    Municipality findById(Long id);
    Municipality update(Long id, String name);
    Municipality delete(Long id);
}
