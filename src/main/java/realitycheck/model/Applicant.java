package realitycheck.model;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author Yuanqun Wang
 *
 */
@Entity
public class Applicant extends User {
	
	public Applicant() {
		
	}
	
	public Applicant(UserType userType, String userName, String email, String password) {
		super(userType, userName, email, password);
	}
}
