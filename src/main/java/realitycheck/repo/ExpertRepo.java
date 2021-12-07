package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Expert;
/**
 * 
 * @author Allan Xie
 *
 */
public interface ExpertRepo extends CrudRepository<Expert, Integer> {
	public Expert findByUserName(String name);
}
