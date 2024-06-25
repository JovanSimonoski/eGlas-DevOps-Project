package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CommitteeMember;

import java.util.List;

public interface CommitteeMemberService {
    CommitteeMember findById(Long id);
    List<CommitteeMember> findAll();
    CommitteeMember update(Long id);
    void delete(Long id);
}
