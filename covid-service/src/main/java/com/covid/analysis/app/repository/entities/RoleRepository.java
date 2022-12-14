package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
