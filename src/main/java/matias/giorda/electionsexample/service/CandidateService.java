package matias.giorda.electionsexample.service;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.exception.DataNotFoundException;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.repository.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CandidateService {

    private final CandidateRepository candidateRepository;

    private final ModelMapper modelMapper;

    public CandidateService(CandidateRepository candidateRepository, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Page<Candidate> findAll(Pageable pageable) {
        return candidateRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Candidate> findOne(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate getCandidate(Long candidateId) throws DataNotFoundException {
        return this.findOne(candidateId)
                .orElseThrow(() -> new DataNotFoundException("Candidate not found :: " + candidateId));
    }
    public Candidate save(CandidateDTO candidateDTO) throws DataNotFoundException {
        return candidateRepository.saveAndFlush(convertToEntity(candidateDTO, Optional.empty()));
    }

    public Candidate update(Long candidateId, CandidateDTO candidateDTO) throws DataNotFoundException {
        return candidateRepository.save(convertToEntity(candidateDTO, Optional.of(candidateId)));
    }

    public Candidate delete(Long candidateId) throws DataNotFoundException {
        Candidate candidate = getCandidate(candidateId);
        candidateRepository.delete(candidate);
        return candidate;
    }

    private Candidate convertToEntity(CandidateDTO candidateDTO, Optional<Long> candidateId) throws DataNotFoundException {
        Optional<Candidate> candidateOptional = candidateId.map(this::findOne).orElse(Optional.of(new Candidate()));
        Candidate candidate = candidateOptional
                .orElseThrow(() -> new DataNotFoundException("Candidate not found to convert to entity :: " + candidateId));
        modelMapper.map(candidateDTO, candidate);
        return candidate;
    }

}
