package realitycheck.model;


import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author Yuanqun Wang
 *
 */
@Entity
@DynamicUpdate
public class Application {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	private String applicantName;
	private Category category;
	private String link;
	private String institutionEmail;
	private boolean isApproved;
	
	public Application() {
		
	}
	
	public Application(String applicantName, Category category, String link, String institutionEmail) {
		this.category = category;
		this.link = link;
		this.institutionEmail = institutionEmail;
		this.applicantName = applicantName;
		this.isApproved = false;
	}
	
	public void approve() {
		this.isApproved = true;
	}
	
	public boolean getApprove() {
		return this.isApproved;
	}
	
	public String getApplicantName() {
		return this.applicantName;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public String getInstitutionEmail() {
		return this.institutionEmail;
	}
	
	public Integer getId() {
		return this.id;
	}
}
