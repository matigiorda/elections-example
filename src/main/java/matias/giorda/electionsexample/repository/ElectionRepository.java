package matias.giorda.electionsexample.repository;

import matias.giorda.electionsexample.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {}
