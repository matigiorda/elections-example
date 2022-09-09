package matias.giorda.electionsexample.controller;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    private final ModelMapper modelMapper;

    private final Integer DEFAULT_PAGE_SIZE = 5;

    public CandidateController(CandidateService candidateService, ModelMapper modelMapper) {
        this.candidateService = candidateService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Page<CandidateDTO> getAllCandidates(@RequestParam(required = false) Integer size,
                                            @RequestParam(required = false) Integer page) {
        return candidateService.findAll(PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE)
        )).map(this::convertToDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidatesById(@PathVariable(value = "id") Long candidateId)
            throws DataNotFoundException {
        Candidate candidate = candidateService.getCandidate(candidateId);
        return ResponseEntity.ok().body(convertToDto(candidate));
    }

    @PostMapping
    public CandidateDTO createCandidate(@RequestBody CandidateDTO candidate) throws DataNotFoundException {
        return convertToDto(candidateService.save(candidate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @PathVariable(value = "id") Long candidateId, @RequestBody CandidateDTO candidate)
            throws DataNotFoundException {
        return ResponseEntity.ok(convertToDto(candidateService.update(candidateId, candidate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CandidateDTO> deleteCandidate(@PathVariable(value = "id") Long candidateId) throws Exception {
        Candidate deletedCandidate = candidateService.delete(candidateId);
        return ResponseEntity.ok(convertToDto(deletedCandidate));
    }

    private CandidateDTO convertToDto(Candidate candidate) {
        return modelMapper.map(candidate, CandidateDTO.class);
    }

}