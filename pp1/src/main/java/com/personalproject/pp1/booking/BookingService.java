package com.personalproject.pp1.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class BookingService {
	private static List<Booking> booking = new ArrayList<Booking>();
	private static int bookingcount = 1;

//	get request for all users
	public List<Booking> findAll() {
		return booking;
	}

//	get request for one user
	public Booking findById(int bookingId) {

		Predicate<? super Booking> predicate = booking -> booking.getBookingId().equals(bookingId);
		return booking.stream().filter(predicate).findFirst().get();
	}

//	create user
	public Booking save(Booking book) {

		book.setBookingId(++bookingcount);
		booking.add(book);
		return book;
	}

	// Get request for bookings by username
	public List<Booking> findByUsername(String username) {
		List<Booking> bookingsByUsername = new ArrayList<>();
		for (Booking booking : booking) {
			if (booking.getUser_name().equals(username)) {
				bookingsByUsername.add(booking);
			}
		}
		return bookingsByUsername;
	}
}
