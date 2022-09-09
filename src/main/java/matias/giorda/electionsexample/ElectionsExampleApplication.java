package matias.giorda.electionsexample;

import matias.giorda.electionsexample.dto.CandidateDTO;
import matias.giorda.electionsexample.model.Candidate;
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
		return modelMapper;
	}

}
