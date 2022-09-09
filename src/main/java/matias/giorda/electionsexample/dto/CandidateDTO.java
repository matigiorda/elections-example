package matias.giorda.electionsexample.dto;

import matias.giorda.electionsexample.model.Candidate;

import javax.validation.constraints.NotNull;

public class CandidateDTO {

    private Long id;

    @NotNull
    private String name;

    public CandidateDTO(){

    }

    public CandidateDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
