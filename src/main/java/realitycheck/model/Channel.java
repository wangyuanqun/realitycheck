package realitycheck.model;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author Allan Xie
 *
 */
@Entity
@DynamicUpdate
public class Channel {

	@Id
    private String channelName;
	
	private int votes;
	private boolean isVerified;
	private boolean isReviewed;
	private String channelLink;
	private String nominateReason;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Category> categories;
	
	private String rejectionExplanation;
	
	public Channel() {
		
	}
	
	public Channel(String channelName) {
		this.channelName = channelName;
		this.categories = new ArrayList<>();
	}
	
	public Channel(String channelName, String channelLink, List<Category> categories, String nominateReason) {
		this.channelName = channelName;
		this.channelLink = channelLink;
		this.categories = categories;
		this.nominateReason = nominateReason;
	}
	
	public Channel(String channelName, String channelLink, List<Category> categories, String nominateReason, boolean isVerified, int votes) {
		this.channelName = channelName;
		this.channelLink = channelLink;
		this.categories = categories;
		this.nominateReason = nominateReason;
		this.isVerified = isVerified;
		this.votes = votes;
	}
	
	public void addVotes() {
		this.votes++;
	}
	
	public void verify(boolean outcome) {
		this.isVerified = outcome;
	}
	
	public boolean getVerified() {
		return this.isVerified;
	}
	
	public void setNominateReason(String reason) {
		this.nominateReason = reason;
	}
	
	public void setRejectionExplanation(String reason) {
		this.rejectionExplanation = reason;
	}
	
	public String getchannelName() {
		return this.channelName;
	}
	
	public int getVotes() {
		return this.votes;
	}
	
	public String getNominateReason() {
		return this.nominateReason;
	}
	
	public List<Category> getCategories() {
		return this.categories;
	}
	
	public void addCategory(Category category) {
			this.categories.add(category);
	}
	
	public String getRejectionExplanation() {
		return this.rejectionExplanation;
	}
	
	public void review() {
		this.isReviewed = true;
	}
	
	public boolean getReviewed() {
		return this.isReviewed;
	}
	
	public String getLink() {
		return this.channelLink;
	}

}
