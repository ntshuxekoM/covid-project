package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.User;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsByIdNumber(String idNumber);
	Optional<User> findByIdNumber(String email);

	@Query("SELECT COUNT(createdDate) from User o where EXTRACT(month FROM o.createdDate) = EXTRACT(month FROM sysdate())")
	int countForMonth();

	long count();
	long countByCreatedDate(Date createdDate);
}