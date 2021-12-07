package realitycheck.repo;
/**
 * @author Yuanqun Wang
 */
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Application;

public interface ApplicationRepo extends CrudRepository<Application, Integer> {
	List<Application> findByApplicantName(String name);
}
