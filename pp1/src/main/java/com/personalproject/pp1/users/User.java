package com.personalproject.pp1.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personalproject.pp1.booking.Booking;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

@Entity(name = "user_details")
public class User {

	@Id
	@GeneratedValue
	private Integer user_id;

	private String first_name;
	private String last_name;
	private String user_name;
	private String password;
	private String role;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Booking> bookings;

	public Integer getUser_id() {
		return user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", first_name=" + first_name + ", last_name=" + last_name + ", user_name="
				+ user_name + ", password=" + password + ", role=" + role + ", bookings=" + bookings + "]";
	}

}
