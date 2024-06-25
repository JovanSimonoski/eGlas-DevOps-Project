package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//@PrimaryKeyJoinColumn
@Table(name = "glasovi_za_lista")
public class CandidatesListVote extends Vote{
    @ManyToOne
    @JoinColumn(name = "kl_id")
    CandidatesList candidatesList;
}
