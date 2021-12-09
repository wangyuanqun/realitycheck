package realitycheck.model;

import javax.persistence.Entity;

@Entity
public class Moderator extends User{

	public Moderator() {

	}

	public Moderator(UserType userType, String userName, String email, String password) {
		super(userType, userName, email, password);
	}
}
