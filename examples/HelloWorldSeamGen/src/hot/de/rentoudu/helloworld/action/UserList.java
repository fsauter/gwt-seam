package de.rentoudu.helloworld.action;

import de.rentoudu.helloworld.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final String EJBQL = "select user from User user";

	private static final String[] RESTRICTIONS = {
			"lower(user.description) like lower(concat(#{userList.user.description},'%'))",
			"lower(user.email) like lower(concat(#{userList.user.email},'%'))",
			"lower(user.name) like lower(concat(#{userList.user.name},'%'))",};

	private User user = new User();

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public User getUser() {
		return user;
	}
}
