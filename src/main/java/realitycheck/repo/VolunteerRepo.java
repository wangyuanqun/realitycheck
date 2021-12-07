package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Volunteer;
/**
 * 
 * @author Sichen Zhao
 *
 */
public interface VolunteerRepo extends CrudRepository<Volunteer, Integer> {
	public Volunteer findByUserName(String name);
}
