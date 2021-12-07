package realitycheck.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author Yuesong Duan
 *
 */
@Entity
@DynamicUpdate
public class Video {
	@Id
    private String videoName;
	
	private int votes;
	private String channelName;
	
	private Queue queue;
	
	public Video() {
		
	}
	
	public Video(String channelName, String videoName) {
		this.votes = 0;
		this.channelName = channelName;
		this.videoName = videoName;
		this.queue = null;
	}
	
	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	
	public Queue getQueue() {
		return this.queue;
	}
	
	public void upVotes() {
		this.votes++;
	}
	
	public void downVotes() {
		this.votes--;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public int getVotes() {
		return this.votes;
	}
	
	public String getVideoName() {
		return this.videoName;
	}
}
