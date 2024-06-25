package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "dokumenti_za_identifikacija")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    Long id;
    @Column(name = "d_broj")
    String documentNumber;
    @Column(name = "d_vazhi_od")
    LocalDate issueDate;
    @Column(name = "d_vazhi_do")
    LocalDate expiryDate;
    @Column(name = "d_izdaden")
    String issuer;
    @OneToOne
    @JoinColumn(name = "g_id")
    Citizen citizen;
}
