package realitycheck.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Channel;
/**
 * 
 * @author Allan Xie
 *
 */
public interface ChannelRepo extends CrudRepository<Channel, Integer> {
	public Channel findByChannelName(String channelName);
	public List<Channel> findByisVerified(boolean isVerified);
}
