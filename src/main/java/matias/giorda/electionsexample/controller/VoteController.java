package matias.giorda.electionsexample.controller;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.dto.VoteDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Vote;
import matias.giorda.electionsexample.service.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    private final ModelMapper modelMapper;

    public VoteController(VoteService voteService, ModelMapper modelMapper) {
        this.voteService = voteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDTO> getCandidatesById(@PathVariable(value = "id") Long voteId)
            throws DataNotFoundException {
        Vote vote = voteService.getVote(voteId);
        return ResponseEntity.ok().body(convertToDto(vote));
    }

    private VoteDTO convertToDto(Vote vote) {
        VoteDTO voteDTO = modelMapper.map(vote, VoteDTO.class);
        voteDTO.setCandidate(modelMapper.map(vote.getCandidate(), CandidateDTO.class));
        return voteDTO;
    }

}