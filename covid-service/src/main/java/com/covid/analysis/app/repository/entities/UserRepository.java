package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsByIdNumber(String idNumber);
	Optional<User> findByIdNumber(String email);
}