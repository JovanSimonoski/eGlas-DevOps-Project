package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.eglas.repository.CandidatesListRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "kandidatski_listi")
public class CandidatesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kl_id")
    Long id;
    @Column(name = "kl_opis")
    String description;
    @ManyToMany
    @JoinTable(name = "e_kandidat_vo", joinColumns = {@JoinColumn(name = "kl_id")},inverseJoinColumns = {@JoinColumn(name = "g_id")})
    public List<Candidate> candidates;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    CandidatesListElectionRealization candidatesListElectionRealization;
    @ManyToOne
    @JoinColumn(name = "ie_id")
    ElectoralUnit electoralUnit;
    @ManyToOne
    @JoinColumn(name = "o_id")
    Municipality municipality;
    @ManyToOne
    @JoinColumn(name = "p_id")
    Party party;

}
