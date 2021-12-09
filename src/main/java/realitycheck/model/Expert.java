package realitycheck.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Expert extends User {

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Category> categories;

	public Expert() {

	}

	public Expert(UserType userType, String userName, String email, String password) {
		super(userType, userName, email, password);
		if(categories == null) {
			this.categories = new ArrayList<>();
		}
	}

	public Expert(UserType userType, String userName, String email, String password, List<Category> categories) {
		super(userType, userName, email, password);
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void addCategory(Category category) {
		this.categories.add(category);
	}
}
