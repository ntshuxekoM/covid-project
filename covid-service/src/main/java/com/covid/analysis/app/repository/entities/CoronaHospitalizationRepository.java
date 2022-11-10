package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.CoronaHospitalization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoronaHospitalizationRepository extends
    JpaRepository<CoronaHospitalization, Long> {

}