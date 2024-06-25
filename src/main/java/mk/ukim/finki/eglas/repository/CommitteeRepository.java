package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Committee;
import mk.ukim.finki.eglas.model.CommitteeMember;
import mk.ukim.finki.eglas.model.ElectionRealization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CommitteeRepository extends JpaRepository<Committee, Long> {
    Committee findCommitteeByMembersContainsAndElectionRealization_DateIsAfter(CommitteeMember committeeMember, LocalDate localDate);
    Committee findCommitteeByMembersContains(CommitteeMember committeeMember);
}
