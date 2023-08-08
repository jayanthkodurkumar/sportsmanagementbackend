package com.personalproject.pp1.jpa;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalproject.pp1.booking.Booking;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, Integer> {
	@Query("SELECT COUNT(b) > 0 FROM booking_details b WHERE  b.date = :date AND b.start_time = :startTime AND  b.end_time = :endTime ")
	boolean existsByDateAndStartTime(@Param("date") LocalDate date, @Param("startTime") LocalTime startTime,
			@Param("endTime") LocalTime endTime);

//	@Query("SELECT COUNT(b) > 0 FROM booking_details b WHERE b.sport_name = :sportName AND b.date = :date AND b.start_time = :startTime")
//	boolean existsBySportNameAndDateAndStartTime(@Param("sportName") String sportName, @Param("date") LocalDate date,
//			@Param("startTime") LocalTime startTime);

}
