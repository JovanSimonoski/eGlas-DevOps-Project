package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "koalicii")
public class Coalition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "koa_id")
    Long id;
    @Column(name = "koa_ime")
    String name;
    @Column(name = "koa_moto")
    String motto;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    ElectionRealization electionRealization;
    @ManyToMany
    @JoinTable(name = "oformuva", joinColumns = {@JoinColumn(name = "koa_id")},inverseJoinColumns = {@JoinColumn(name = "p_id")})
    List<Party> parties;

    public String getPartiesString(){
        return parties.stream().map(p -> p.name).collect(Collectors.joining(", "));
    }
    public Coalition(){
        this.parties = new ArrayList<>();
    }
}
