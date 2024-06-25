package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "realizacii_na_izbori")
public class ElectionRealization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ri_id")
    Long id;
    @Column(name = "ri_datum")
    LocalDate date;
    @Column(name = "ri_ime")
    String name;
    @ManyToOne
    @JoinColumn(name = "i_id")
    Election election;
}
