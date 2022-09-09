package matias.giorda.electionsexample.service;

import matias.giorda.electionsexample.dto.ElectionDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Election;
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

    private final ModelMapper modelMapper;

    public ElectionService(ElectionRepository electionRepository, ModelMapper modelMapper) {
        this.electionRepository = electionRepository;
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
    public Election save(ElectionDTO electionDTO) throws DataNotFoundException {
        return electionRepository.saveAndFlush(convertToEntity(electionDTO, Optional.empty()));
    }

    public Election update(Long electionId, ElectionDTO electionDTO) throws DataNotFoundException {
        return electionRepository.save(convertToEntity(electionDTO, Optional.of(electionId)));
    }

    public Election delete(Long electionId) throws DataNotFoundException {
        Election election = getElection(electionId);
        electionRepository.delete(election);
        return election;
    }

    private Election convertToEntity(ElectionDTO electionDTO, Optional<Long> electionId) throws DataNotFoundException {
        Optional<Election> electionOptional = electionId.map(this::findOne).orElse(Optional.of(new Election()));
        Election election = electionOptional
                .orElseThrow(() -> new DataNotFoundException("Election not found to convert to entity :: " + electionId));
        modelMapper.map(electionDTO, election);
        return election;
    }

}
