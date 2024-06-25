package mk.ukim.finki.eglas.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;

@Service
public class TestProcedures {
    private final EntityManager entityManager;

    public TestProcedures(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Double getTurnoutByRealizationId(long realizationId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("get_turnout_by_realization_in_percentage")
                .registerStoredProcedureParameter("realization_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("turnout_percentage", Double.class, ParameterMode.OUT)
                .setParameter("realization_id", realizationId);

        query.execute();

        return (Double) query.getOutputParameterValue("turnout_percentage");
    }
}
