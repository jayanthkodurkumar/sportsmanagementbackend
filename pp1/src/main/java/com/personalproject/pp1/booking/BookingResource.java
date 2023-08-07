package com.personalproject.pp1.booking;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.personalproject.pp1.jpa.BookingJpaRepository;
import com.personalproject.pp1.jpa.UserJpaRepository;
import com.personalproject.pp1.users.User;

@RestController
public class BookingResource {

	private BookingJpaRepository bookingrepository;
	private UserJpaRepository userRepository;

	public BookingResource(BookingJpaRepository bookingrepository, UserJpaRepository userRepository) {
		super();
		this.bookingrepository = bookingrepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/booking")
	public List<Booking> retrieveAllBooking() {
		return bookingrepository.findAll();
	}

	@GetMapping("/booking/{bookingId}")
	public EntityModel<Booking> retrieveBooking(@PathVariable int bookingId) {
		Optional<Booking> booking = bookingrepository.findById(bookingId);
		EntityModel<Booking> entityModel = EntityModel.of(booking.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllBooking());
		entityModel.add(link.withRel("all-bookings"));

		return entityModel;
	}

	@PostMapping("/users/{user_id}/booking")
	public ResponseEntity<Object> createBooking(@PathVariable("user_id") Integer userId, @RequestBody Booking booking) {
		Optional<User> user = userRepository.findById(userId);
//		if (!user.isPresent()) {
//			throw new UserNotFoundException("User not found with id: " + userId);
//		}

		booking.setUser(user.get());

		String userName = booking.getUser_name();
		LocalDate date = booking.getDate();
		LocalTime startTime = booking.getStart_time();

		// Check if a booking with the specified sportName, date, and startTime already
		// exists
		boolean userbookingExists = bookingrepository.existsByUserNameAndDateAndStartTime(userName, date, startTime);

		if (userbookingExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Booking already exists for the specified user, date, and start time");
		}

//		String sportName = booking.getSport_name();
//
//		boolean bookingExists = bookingrepository.existsBySportNameAndDateAndStartTime(sportName, date, startTime);

//		if (bookingExists) {
//			return ResponseEntity.status(HttpStatus.CONFLICT)
//					.body("Booking already exists for the specified sport, date, and start time");
//		}

		Booking savedBooking = bookingrepository.save(booking);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedBooking.getBookingId()).toUri();

		return ResponseEntity.created(location).build();
	}

// get booking by user id
	@GetMapping("/users/{user_id}/bookings")
	public List<Booking> getBookingsByUserId(@PathVariable("user_id") Integer userId) {
		List<Booking> userbooking = new ArrayList<Booking>();
		List<Booking> allbooking = bookingrepository.findAll();

		for (Booking booking : allbooking) {
			if (booking.getUser().getUser_id().equals(userId)) {
				userbooking.add(booking);
			}
		}
		return userbooking;
	}

//	delete booking
	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<Object> deleteBooking(@PathVariable("bookingId") int bookingId) {
		Optional<Booking> booking = bookingrepository.findById(bookingId);

		if (booking.isPresent()) {
			bookingrepository.delete(booking.get());
			return ResponseEntity.accepted().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
