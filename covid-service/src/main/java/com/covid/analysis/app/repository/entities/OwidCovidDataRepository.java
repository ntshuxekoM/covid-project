package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.OwidCovidData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwidCovidDataRepository extends JpaRepository<OwidCovidData, Long> {

}