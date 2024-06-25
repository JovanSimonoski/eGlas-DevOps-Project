package mk.ukim.finki.eglas.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Cleanup;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDate;

@Entity
@Subselect("select * from turnout_by_municipality")
@Immutable
@Data
public class TotalTurnoutByMunicipality {
    @Id
    @Column(name = "row_num")
    Long id;
    @Column(name = "o_id")
    Long municipalityId;
    @Column(name = "map_id")
    String map_id;
    @Column(name = "ri_id")
    Long realizationId;
    @Column(name = "o_ime")
    String municipalityName;
    @Column(name = "ri_datum")
    LocalDate realizationDate;
    @Column(name = "ri_ime")
    String realizationName;
    @Column(name = "total")
    Double total;
}
