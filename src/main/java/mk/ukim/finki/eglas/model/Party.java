package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "partii")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    Long id;
    @Column(name = "p_ime")
    String name;
    @OneToOne
    @JoinColumn(name = "a_id")
    Address address;
}
