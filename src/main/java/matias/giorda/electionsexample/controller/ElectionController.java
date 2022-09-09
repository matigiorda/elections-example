package matias.giorda.electionsexample.controller;

import matias.giorda.electionsexample.dto.ElectionDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Election;
import matias.giorda.electionsexample.service.ElectionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class ElectionController {

    private final ElectionService electionService;

    private final ModelMapper modelMapper;

    private final Integer DEFAULT_PAGE_SIZE = 5;

    public ElectionController(ElectionService electionService, ModelMapper modelMapper) {
        this.electionService = electionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Page<ElectionDTO> getAllElections(@RequestParam(required = false) Integer size,
                                            @RequestParam(required = false) Integer page) {
        return electionService.findAll(PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE)
        )).map(this::convertToDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionDTO> getElectionsById(@PathVariable(value = "id") Long electionId)
            throws DataNotFoundException {
        Election election = electionService.getElection(electionId);
        return ResponseEntity.ok().body(convertToDto(election));
    }

    @PostMapping
    public ElectionDTO createElection(@RequestBody ElectionDTO election) throws DataNotFoundException {
        return convertToDto(electionService.save(election));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElectionDTO> updateElection(
            @PathVariable(value = "id") Long electionId, @RequestBody ElectionDTO election)
            throws DataNotFoundException {
        return ResponseEntity.ok(convertToDto(electionService.update(electionId, election)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ElectionDTO> deleteElection(@PathVariable(value = "id") Long electionId) throws Exception {
        Election deletedElection = electionService.delete(electionId);
        return ResponseEntity.ok(convertToDto(deletedElection));
    }

    private ElectionDTO convertToDto(Election election) {
        return modelMapper.map(election, ElectionDTO.class);
    }

}