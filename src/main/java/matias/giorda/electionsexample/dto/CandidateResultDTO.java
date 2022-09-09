package matias.giorda.electionsexample.dto;

import matias.giorda.electionsexample.model.Candidate;

public class CandidateResultDTO {

    private CandidateDTO candidate;
    private Long count;

    public CandidateResultDTO(Candidate candidate, Long count) {
        this.candidate = new CandidateDTO(candidate);
        this.count = count;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
