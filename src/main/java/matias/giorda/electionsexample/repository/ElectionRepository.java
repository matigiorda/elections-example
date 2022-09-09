package matias.giorda.electionsexample.repository;

import matias.giorda.electionsexample.dto.CandidateResultDTO;
import matias.giorda.electionsexample.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
    @Query("SELECT new matias.giorda.electionsexample.dto.CandidateResultDTO(v.candidate, COUNT(v)) " +
            "FROM Election e " +
            "INNER JOIN e.votes v " +
            "WHERE e.id = :electionId " +
            "GROUP BY v.candidate " +
            "ORDER BY COUNT(v)")
    List<CandidateResultDTO> getElectionVotesByCandidate(Long electionId);
}
