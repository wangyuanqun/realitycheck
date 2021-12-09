package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Applicant;
import realitycheck.model.Expert;

public interface ApplicantRepo extends CrudRepository<Applicant, Integer> {
	public Applicant findByUserName(String name);
}
