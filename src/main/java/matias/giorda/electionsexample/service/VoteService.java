package matias.giorda.electionsexample.service;

import matias.giorda.electionsexample.dto.VoteDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.model.Vote;
import matias.giorda.electionsexample.repository.VoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;

    private final ModelMapper modelMapper;

    public VoteService(VoteRepository voteRepository, ModelMapper modelMapper) {
        this.voteRepository = voteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Optional<Vote> findOne(Long id) {
        return voteRepository.findById(id);
    }

    public Vote getVote(Long voteId) throws DataNotFoundException {
        return this.findOne(voteId)
                .orElseThrow(() -> new DataNotFoundException("Election not found :: " + voteId));
    }

    public Vote convertToEntity(VoteDTO voteDTO, Candidate candidate) {
        Vote vote = modelMapper.map(voteDTO, Vote.class);
        vote.setCandidate(candidate);
        return vote;
    }
}
