package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.VoteIdentificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface VoteIdentificationCodeRepository extends JpaRepository<VoteIdentificationCode, UUID> {
    @Query(value = "select * from kodovi_za_identifikacija where used = false order by random() limit 1", nativeQuery = true)
    Optional<VoteIdentificationCode> findRandomVoteIdentificationCode();
}
