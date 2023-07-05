package com.personalproject.pp1.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sportsmanagementapplication.sportsmanagementapp.booking.Booking;
import com.sportsmanagementapplication.sportsmanagementapp.jpa.BookingJpaRepository;
import com.sportsmanagementapplication.sportsmanagementapp.jpa.UserJpaRepository;

@RestController
public class UserResource {

	private UserJpaRepository userrepository;
	private BookingJpaRepository bookingrepository;

	public UserResource(UserJpaRepository userrepository, BookingJpaRepository bookingrepository) {
		super();
		this.userrepository = userrepository;
		this.bookingrepository = bookingrepository;
	}

//	get /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {

		return userrepository.findAll();
//		return userservice.findAll();
	}

//	get by id /users

//	entity model
//	webmvcbuilder
	@GetMapping("/users/{userId}")
	public ResponseEntity<?> retrieveUser(@PathVariable int userId) {
		boolean userIdExists = userrepository.existsById(userId);
		if (!userIdExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User does not exist");
		}
		Optional<User> user = userrepository.findById(userId);
		EntityModel<User> entitymodel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entitymodel.add(link.withRel("all-users"));
		return ResponseEntity.ok(entitymodel);
//		return entitymodel;
	}

//	post request for creating user
	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User user) {

		String userName = user.getUser_name();
		boolean userExists = userrepository.existsByusername(userName);

		if (userExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

		}

		User savedUser = userrepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(savedUser.getUserId()).toUri();

		return ResponseEntity.created(location).build();
	}

//	delete by id

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteBooking(@PathVariable int userId) {
		Optional<User> user = userrepository.findById(userId);

		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
		}

		User foundUser = user.get();
		List<Booking> userBookings = foundUser.getBookings();

		// Delete the associated bookings
		for (Booking booking : userBookings) {
			bookingrepository.delete(booking);
		}

		userrepository.deleteById(userId);

		List<User> updatedUser = userrepository.findAll();
		return ResponseEntity.ok(updatedUser);
	}

}
