package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "kodovi")
public class VotingCode {
    @Id
    @Column(name = "kod_kod")
    String code;
    @JoinColumn(name = "ri_id")
    @ManyToOne
    ElectionRealization electionRealization;
    @JoinColumn(name = "g_id")
    @ManyToOne
    Citizen citizen;
    @Column(name = "kod_vazi_do")
    LocalDateTime expiryTime;
    public VotingCode(Citizen citizen, ElectionRealization electionRealization) {
        this.code = UUID.randomUUID().toString();
        this.citizen = citizen;
        this.electionRealization = electionRealization;
        this.expiryTime = LocalDateTime.now().plusMinutes(10);
    }
}
