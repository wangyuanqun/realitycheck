package realitycheck.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

	private String videoName;

	private int upVote;
	private int downVote;
	private String content;

	public Comment() {

	}


	public Comment(String videoName, String content) {
		this.videoName = videoName;
		this.content = content;
		this.upVote = 0;
		this.downVote = 0;
	}

	public int getUpVote() {
		return this.upVote;
	}

	public int getDownVote() {
		return this.downVote;
	}

	public void setUpVote() {
		this.upVote++;
	}

	public void setDownVote() {
		this.downVote++;
	}

	public String getContent() {
		return this.content;
	}

	public Integer getId() {
		return this.id;
	}

	public String getVideoName() {
		return this.videoName;
	}
}
