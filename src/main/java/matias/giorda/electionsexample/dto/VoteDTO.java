package matias.giorda.electionsexample.dto;

public class VoteDTO {

    private Long id;

    private CandidateDTO candidate;

    public VoteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

}
