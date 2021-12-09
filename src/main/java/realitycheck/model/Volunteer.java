package realitycheck.model;

import javax.persistence.Entity;

@Entity
public class Volunteer extends User {

	public Volunteer() {

	}

	public Volunteer(UserType userType, String userName, String email, String password) {
		super(userType, userName, email, password);
	}
}
