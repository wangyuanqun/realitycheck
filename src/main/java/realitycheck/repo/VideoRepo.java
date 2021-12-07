package realitycheck.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Video;
/**
 * 
 * @author Yuesong Duan
 *
 */
public interface VideoRepo extends CrudRepository<Video, Integer> {
	public List<Video> findByChannelName(String name);
	public Video findByVideoName(String name);
}
