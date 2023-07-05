package com.personalproject.pp1.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	private static List<User> users = new ArrayList<>();
	private static int userCount = 0;

	public List<User> findAll() {
		return users;
	}

	public User findById(int userId) {
		Optional<User> optionalUser = users.stream().filter(user -> user.getUser_id().equals(userId)).findFirst();
		return optionalUser.orElse(null);
	}

	public User save(User user) {
		user.setUser_id(userCount++);
		users.add(user);
		return user;
	}

	public User deleteById(int userId) {
		Optional<User> optionalUser = users.stream().filter(user -> user.getUser_id().equals(userId)).findFirst();
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			users.remove(user);
			return user;
		}
		return null;
	}

	public User findByUsername(String username) {
		Optional<User> optionalUser = users.stream().filter(user -> user.getUser_name().equals(username)).findFirst();
		return optionalUser.orElse(null);
	}
}
