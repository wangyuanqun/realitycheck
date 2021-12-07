package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Moderator;
/**
 * 
 * @author Yuesong Duan
 *
 */
public interface ModeratorRepo extends CrudRepository<Moderator, Integer> {
	public Moderator findByUserName(String name);

}
