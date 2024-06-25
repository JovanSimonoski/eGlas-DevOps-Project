package mk.ukim.finki.eglas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.eglas.model.Citizen;

import java.time.LocalDateTime;

@Data
public class CitizenVote {
    private Citizen citizen;
    private LocalDateTime timeVoted;

    public CitizenVote(Citizen citizen, LocalDateTime timeVoted) {
        this.citizen = citizen;
        this.timeVoted = timeVoted;
    }
}
