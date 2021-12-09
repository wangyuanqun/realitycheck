package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Expert;

public interface ExpertRepo extends CrudRepository<Expert, Integer> {
	public Expert findByUserName(String name);
}
