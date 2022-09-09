package matias.giorda.electionsexample;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.dto.ElectionDTO;
import matias.giorda.electionsexample.dto.VoteDTO;
import matias.giorda.electionsexample.model.Candidate;
import matias.giorda.electionsexample.model.Election;
import matias.giorda.electionsexample.model.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElectionsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectionsExampleApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(CandidateDTO.class, Candidate.class).addMappings(mapper -> mapper.skip(Candidate::setId));
		modelMapper.typeMap(ElectionDTO.class, Election.class).addMappings(mapper -> mapper.skip(Election::setId));
		modelMapper.typeMap(ElectionDTO.class, Election.class).addMappings(mapper -> mapper.skip(Election::setVotes));
		modelMapper.typeMap(VoteDTO.class, Vote.class).addMappings(mapper -> mapper.skip(Vote::setCandidate));
		modelMapper.typeMap(Vote.class, VoteDTO.class).addMappings(mapper -> mapper.skip(VoteDTO::setCandidate));
		return modelMapper;
	}

}
