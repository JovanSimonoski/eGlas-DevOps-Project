package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "glasanja")
public class Turnout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ug_id")
    Long id;
    @Column(name = "ug_vreme")
    LocalDateTime voteTimestamp;
    @ManyToOne
    @JoinColumn(name = "g_id")
    Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    ElectionRealization electionRealization;
    @ManyToOne
    @JoinColumn(name = "im_id")
    PollingStation pollingStation;
}
