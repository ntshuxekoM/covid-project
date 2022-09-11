package com.covid.analysis.app.controller;

import com.covid.analysis.app.model.entities.User;
import com.covid.analysis.app.payload.DashboardData;
import com.covid.analysis.app.payload.MessageResponse;
import com.covid.analysis.app.repository.entities.UserRepository;
import com.covid.analysis.app.service.DashboardService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/get-dashboard-data/{userId}")
    public ResponseEntity<?> getDashboardData(@PathVariable Long userId) {
        LOGGER.info("Get Dashboard data. User: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        DashboardData dashboardData = new DashboardData();

        if (optionalUser.isPresent()) {
            dashboardData.setAffected(dashboardService.getAffected());
            dashboardData.setRecoveries(dashboardService.getRecoveries());
            dashboardData.setCasualties(dashboardService.getCasualties());
            dashboardData.setFuturePrediction(dashboardService.getFuturePrediction());
            dashboardData.setVaccinationData(dashboardService.getVaccinationData());
            if (dashboardService.hasAdminRight(optionalUser.get().getRoles())) {
                dashboardData.setRegisteredUser(dashboardService.getRegisteredUser());
                dashboardData.setRegisteredUserList(dashboardService.getRegisteredUserList());
            }
            LOGGER.info("Get Dashboard data. User: {}, Data: {}", userId, dashboardData);
            return ResponseEntity.ok(dashboardData);

        } else {
            LOGGER.error("User not found, User ID: {}", userId);
            return ResponseEntity.badRequest()
                .body(new MessageResponse(false, "Error: user details not found"));
        }

    }

}
