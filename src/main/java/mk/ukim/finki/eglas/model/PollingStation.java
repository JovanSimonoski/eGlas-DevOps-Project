package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "izbiracki_mesta")
public class PollingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "im_id")
    Long id;
    @Column(name = "im_mesto")
    String name;
    @ManyToOne
    @JoinColumn(name = "ie_id")
    ElectoralUnit electoralUnit;
    @ManyToOne
    @JoinColumn(name = "a_id", nullable = true)
    Address address;


    @Override
    public String toString() {
        return "PollingStation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}