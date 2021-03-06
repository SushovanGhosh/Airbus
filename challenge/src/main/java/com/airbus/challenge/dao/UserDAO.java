package com.airbus.challenge.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airbus.challenge.entity.UserEntity;


@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByUsernameOrEmail(String username, String email);
	
	List<UserEntity> findByIdIn(List<Long> userIds);
	
	Optional<UserEntity> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
}
