package realitycheck.repo;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Volunteer;

public interface VolunteerRepo extends CrudRepository<Volunteer, Integer> {
	public Volunteer findByUserName(String name);
}
