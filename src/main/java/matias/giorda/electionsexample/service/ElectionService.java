package matias.giorda.electionsexample.service;

import matias.giorda.electionsexample.dto.ElectionDTO;
import matias.giorda.electionsexample.dto.ElectionResultDTO;
import matias.giorda.electionsexample.dto.VoteDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.model.Election;
import matias.giorda.electionsexample.model.Vote;
import matias.giorda.electionsexample.repository.ElectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ElectionService {

    private final ElectionRepository electionRepository;

    private final CandidateService candidateService;
    private final VoteService voteService;
    private final ModelMapper modelMapper;

    public ElectionService(ElectionRepository electionRepository, CandidateService candidateService, VoteService voteService, ModelMapper modelMapper) {
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
        this.voteService = voteService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Page<Election> findAll(Pageable pageable) {
        return electionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Election> findOne(Long id) {
        return electionRepository.findById(id);
    }

    public Election getElection(Long electionId) throws DataNotFoundException {
        return this.findOne(electionId)
                .orElseThrow(() -> new DataNotFoundException("Election not found :: " + electionId));
    }
    public Election save(ElectionDTO electionDTO) {
        return electionRepository.saveAndFlush(convertToEntity(electionDTO, new Election()));
    }

    public Election update(Long electionId, ElectionDTO electionDTO) throws DataNotFoundException {
        return persist(convertToEntity(electionDTO, findOne(electionId)
                .orElseThrow(() -> new DataNotFoundException("Election not found to update :: " + electionId))));
    }

    public Election persist(Election election) {
        return electionRepository.save(election);
    }

    public Election delete(Long electionId) throws DataNotFoundException {
        Election election = getElection(electionId);
        electionRepository.delete(election);
        return election;
    }

    private Election convertToEntity(ElectionDTO electionDTO, Election election) {
        modelMapper.map(electionDTO, election);
        return election;
    }

    public Election addVote(VoteDTO voteDTO, Long electionId) throws DataNotFoundException {
        Election election = getElection(electionId);
        Candidate candidate = candidateService.getCandidate(voteDTO.getCandidate().getId());
        Vote vote = voteService.convertToEntity(voteDTO, candidate);
        election.addVote(vote);
        return persist(election);
    }

    public ElectionResultDTO getElectionResult(Long electionId) {
        return new ElectionResultDTO(electionRepository.getElectionVotesByCandidate(electionId));
    }
}
