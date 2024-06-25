package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "kodovi_za_identifikacija")
public class VoteIdentificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Boolean used;
    private LocalDateTime validUntil;

    public VoteIdentificationCode(LocalDateTime validUntil) {
        this.validUntil = validUntil;
        this.used = false;
    }

    public VoteIdentificationCode() {

    }
}
