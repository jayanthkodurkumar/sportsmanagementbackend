package com.personalproject.pp1.login;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.pp1.jpa.UserJpaRepository;
import com.personalproject.pp1.users.User;

@RestController
public class LoginResource {
	private UserJpaRepository userrepository;
	private PasswordEncoder passwordEncoder;

	public LoginResource(UserJpaRepository userrepository, PasswordEncoder passwordEncoder) {
		super();
		this.userrepository = userrepository;
		this.passwordEncoder = passwordEncoder;
	}

//	post request for login
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody User user) {

		String username = user.getUser_name();
		String password = user.getPassword();

		boolean userExists = userrepository.existsByusername(username);

		if (!userExists) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("No user found with the username " + '"' + username + '"');
		}

		User dbUser = userrepository.findByUserName(username);

		if (passwordEncoder.matches(password, dbUser.getPassword())) {
			return ResponseEntity.ok(dbUser);

		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");

	}
}
