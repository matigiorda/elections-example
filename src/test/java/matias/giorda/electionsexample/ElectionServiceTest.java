package matias.giorda.electionsexample;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.dto.VoteDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.model.Election;
import matias.giorda.electionsexample.model.Vote;
import matias.giorda.electionsexample.repository.ElectionRepository;
import matias.giorda.electionsexample.service.CandidateService;
import matias.giorda.electionsexample.service.ElectionService;
import matias.giorda.electionsexample.service.VoteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class ElectionServiceTest {

	@Autowired
	private ElectionService electionService;

	@MockBean
	private VoteService voteService;

	@MockBean
	private ElectionRepository electionRepository;

	@MockBean
	private CandidateService candidateService;

	@Test
	void contextLoads() {
	}

	private Election electionWithNoVotes() {
		Election noVotesElection = new Election();
		noVotesElection.setName("TEST");
		noVotesElection.setVotes(new ArrayList<>());
		return noVotesElection;
	}

	@Test
	void givenAElectionWithNoVotesWhenIAddAVoteThenItsVoteCountHasOneElement() throws DataNotFoundException {
		Election electionWithNoVotes = electionWithNoVotes();
		Mockito.when(electionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(electionWithNoVotes));
		Mockito.when(candidateService.getCandidate(Mockito.anyLong())).thenReturn(new Candidate());

		VoteDTO voteMock = getVoteMock();
		Vote voteEntity = new Vote();
		Mockito.when(voteService.convertToEntity(Mockito.any(), Mockito.any())).thenReturn(voteEntity);
		electionService.addVote(voteMock, 1L);

		Assertions.assertThat(electionWithNoVotes.getVotes()).containsExactly(voteEntity);
	}

	private static VoteDTO getVoteMock() {
		VoteDTO voteMock = new VoteDTO();
		voteMock.setCandidate(new CandidateDTO());
		return voteMock;
	}

}
