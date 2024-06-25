package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "opstini")
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    Long id;
    @Column(name = "map_id")
    String mapId;
    @Column(name = "o_ime")
    String name;
}
