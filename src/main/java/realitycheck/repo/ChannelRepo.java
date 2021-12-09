package realitycheck.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Channel;

public interface ChannelRepo extends CrudRepository<Channel, Integer> {
	public Channel findByChannelName(String channelName);
	public List<Channel> findByisVerified(boolean isVerified);
}
