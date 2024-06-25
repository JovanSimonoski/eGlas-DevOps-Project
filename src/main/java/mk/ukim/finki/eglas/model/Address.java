package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Data
@NoArgsConstructor
@Table(name = "adresi")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    Long id;
    @Column(name = "a_ulica")
    String street;
    @Column(name = "a_broj")
    Integer houseNumber;
    @Column(name = "a_vlez")
    Integer entranceNumber;
    @Column(name = "a_stan")
    Integer  apartmentNumber;
    @ManyToOne
    @JoinColumn(name = "o_id")
    Municipality municipality;
    @ManyToOne
    @JoinColumn(name = "im_id")
    PollingStation pollingStation;
}
