package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "glasovi")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gl_id")
    private Long id;
    @Column(name = "ug_vreme")
    private LocalDateTime voteTimestamp;
    @ManyToOne
    @JoinColumn(name = "id_kod")
    private VoteIdentificationCode voteIdentificationCode;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    private ElectionRealization electionRealization;
    @ManyToOne
    @JoinColumn(name = "im_id")
    public PollingStation pollingStation;
}
