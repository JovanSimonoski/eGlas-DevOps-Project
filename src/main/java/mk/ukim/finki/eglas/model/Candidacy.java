package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "kandidaturi")
public class Candidacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kan_id")
    Long id;
    @Column(name = "kan_opis")
    String description;
    @ManyToOne
    @JoinColumn(name = "g_id")
    Candidate candidate;
    @ManyToOne
    @JoinColumn(name = "p_id")
    Party party;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    CandidatesElectionRealization candidatesElectionRealization;
    @ManyToOne
    @JoinColumn(name = "o_id")
    Municipality municipality;
}
