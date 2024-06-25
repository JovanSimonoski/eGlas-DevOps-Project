package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gragjani")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_id")
    Long id;
    @Column(name = "g_ime")
    String name;
    @Column(name = "g_prezime")
    String surname;
    @Column(name = "g_datum_ragjanje")
    LocalDate dateOfBirth;
    @Column(name = "g_embg")
    String idNum;
    @ManyToOne
    @JoinColumn(name = "a_id")
    Address address;
//    @Column(name = "g_godini")
//    Integer yearsAge;
    @OneToOne(mappedBy = "citizen", fetch = FetchType.LAZY)
    @JoinColumn(name = "d_broj")
    Documents document;

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", idNum='" + idNum + '\'' +
                ", address=" + address +
                '}';
    }

    public String type()
    {
        return "citizen";
    }
}
