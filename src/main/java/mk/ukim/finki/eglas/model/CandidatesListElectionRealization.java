package mk.ukim.finki.eglas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
//@PrimaryKeyJoinColumn
@Table(name = "realizacii_so_kandidatska_lista")
public class CandidatesListElectionRealization extends ElectionRealization{
}
