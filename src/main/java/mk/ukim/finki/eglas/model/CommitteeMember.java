package mk.ukim.finki.eglas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn
@Table(name = "clenovi_na_komisija")
public class CommitteeMember extends Citizen {
    @Override
    public String type() {
        return "committee";
    }
}
