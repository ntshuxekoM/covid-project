package com.covid.analysis.app.repository.lookups;
import com.covid.analysis.app.model.lookups.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAppRepository extends JpaRepository<AppConfig, Long> {
    Boolean existsByDescription(String description);
}
