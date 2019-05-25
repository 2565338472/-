package com.sansan.toothbrush.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sansan.toothbrush.machine.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "select u.* from ss_user as u where u.user_name=:username", nativeQuery = true)
	User findByUsername(@Param("username") String username);
}
