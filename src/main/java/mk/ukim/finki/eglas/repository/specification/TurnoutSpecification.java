package mk.ukim.finki.eglas.repository.specification;

import mk.ukim.finki.eglas.model.Turnout;
import org.springframework.data.jpa.domain.Specification;

public class TurnoutSpecification {
    public static Specification<Turnout> hasMunicipalityId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("municipality"), id);
    }
}
