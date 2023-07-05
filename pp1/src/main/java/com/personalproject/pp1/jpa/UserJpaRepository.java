package com.personalproject.pp1.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personalproject.pp1.users.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {
	@Query("SELECT COUNT(u) > 0 FROM user_details u WHERE u.user_name = :userName")
	boolean existsByusername(@Param("userName") String userName);

	@Query("SELECT u FROM user_details u WHERE u.user_name = :userName")
	User findByUserName(@Param("userName") String userName);

}
