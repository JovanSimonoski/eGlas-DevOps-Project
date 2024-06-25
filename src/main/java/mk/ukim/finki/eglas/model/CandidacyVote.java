package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn
@Table(name = "glasovi_za_kandidat")
public class CandidacyVote extends Vote{
    @ManyToOne
    @JoinColumn(name = "kan_id")
    private Candidacy candidacy;
}
