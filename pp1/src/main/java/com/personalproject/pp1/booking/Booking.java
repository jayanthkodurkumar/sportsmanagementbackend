package com.personalproject.pp1.booking;

import java.time.LocalDate;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personalproject.pp1.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "booking_details")
public class Booking {

	@Id
	@GeneratedValue
	private Integer booking_id;

	private String first_name;
	private String last_name;
	private String user_name;

	private String sport_name;
	private LocalDate date;

	private LocalTime start_time;
	private LocalTime end_time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;

	protected Booking() {
	}

	public Integer getBookingId() {
		return booking_id;
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

	public String getSport_name() {
		return sport_name;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setBookingId(Integer booking_id) {
		this.booking_id = booking_id;
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

	public void setSport_name(String sport_name) {
		this.sport_name = sport_name;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Booking [booking_id=" + booking_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", user_name=" + user_name + ", sport_name=" + sport_name + ", date=" + date + ", start_time="
				+ start_time + ", end_time=" + end_time + ", user=" + user + "]";
	}

}