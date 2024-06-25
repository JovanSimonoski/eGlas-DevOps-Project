package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "komisii")
public class Committee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kom_id")
    Long id;
    @OneToOne
    @JoinColumn(name = "im_id")
    PollingStation pollingStation;
    @ManyToOne
    @JoinColumn(name = "ri_id")
    ElectionRealization electionRealization;
    @ManyToMany
    @JoinTable(name = "se_clenovi_na", joinColumns = {@JoinColumn(name = "kom_id")}, inverseJoinColumns = {@JoinColumn(name = "g_id")})
    List<CommitteeMember> members;

    public String getCommitteeMembers(){
        return members.stream().map(p -> (p.name + " " + p.surname)).collect(Collectors.joining(", "));
    }

    public Committee(){
        this.members = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Committee{" +
                "id=" + id +
                ", pollingStation=" + pollingStation +
                '}';
    }
}
