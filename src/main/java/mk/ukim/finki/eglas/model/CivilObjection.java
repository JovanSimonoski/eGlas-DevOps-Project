package mk.ukim.finki.eglas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "prigovori_od_gragjanin")
public class CivilObjection extends Objection {
    @ManyToOne
    @JoinColumn(name = "g_id")
    Citizen citizen;
}
