package realitycheck.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

	private UserType userType;


	private String userName;

	private String email;

	private String password;

	public User() {

	}

	public User(UserType userType, String userName, String email, String password) {
		this.userType = userType;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getId() {
        return this.id;
    }

}
