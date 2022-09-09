package matias.giorda.electionsexample.dto;

import java.util.List;

public class ElectionResultDTO {

    private List<CandidateResultDTO> results;

    public ElectionResultDTO(List<CandidateResultDTO> results) {
        this.results = results;
    }

    public List<CandidateResultDTO> getResults() {
        return results;
    }

    public void setResults(List<CandidateResultDTO> results) {
        this.results = results;
    }
}
