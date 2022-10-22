package com.covid.analysis.app.controller;

import com.covid.analysis.app.payload.DashboardData;
import com.covid.analysis.app.payload.FuturePrediction;
import com.covid.analysis.app.payload.VaccinationData;
import com.covid.analysis.app.repository.entities.UserRepository;
import com.covid.analysis.app.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-dashboard-data")
    public ResponseEntity<?> getDashboardData() {
        DashboardData dashboardData = new DashboardData();
        dashboardData.setAffected(dashboardService.getAffected());
        dashboardData.setRecoveries(dashboardService.getRecoveries());
        dashboardData.setCasualties(dashboardService.getCasualties());
        dashboardData.setRegisteredUser(dashboardService.getRegisteredUser());
        LOGGER.info("Get Dashboard data. Data: {}", dashboardData);
        return ResponseEntity.ok(dashboardData);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-future-prediction")
    public ResponseEntity<?> getFuturePrediction() {
        FuturePrediction prediction = dashboardService.getFuturePrediction();
        LOGGER.info("Get Future Prediction. Data: {}", prediction);
        return ResponseEntity.ok(prediction);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-get-vaccination-data")
    public ResponseEntity<?> getVaccinationData() {
        VaccinationData vaccinationData = dashboardService.getVaccinationData();
        LOGGER.info("Get Vaccination Data: {}", vaccinationData);
        return ResponseEntity.ok(vaccinationData);
    }

}
