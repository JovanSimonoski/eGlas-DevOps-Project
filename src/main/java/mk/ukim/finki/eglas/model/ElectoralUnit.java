package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "izborni_edinici")
public class ElectoralUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ie_id")
    Long id;
    @Column(name = "ie_broj_pratenici")
    Integer numDeputies;
    @Column(name = "ie_ime")
    String name;

    public ElectoralUnit(String name, Integer numDeputies) {
        this.numDeputies = numDeputies;
        this.name = name;
    }
}
