package mk.ukim.finki.eglas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn
@Table(name = "kandidati")
public class Candidate extends Citizen{
    @Override
    public String type() {
        return "candidate";
    }
}
