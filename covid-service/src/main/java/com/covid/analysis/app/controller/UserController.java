package com.covid.analysis.app.controller;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.entities.User;
import com.covid.analysis.app.payload.ChangePassRequest;
import com.covid.analysis.app.payload.MessageResponse;
import com.covid.analysis.app.payload.UserDetails;
import com.covid.analysis.app.repository.entities.UserRepository;
import com.covid.analysis.app.validator.ValidatorService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidatorService validatorService;
    @Autowired
    private PasswordEncoder encoder;


    /**
     * This method find user details by user ID
     * Only users with general role (ROLE_USER) can access this method
     * */
    @GetMapping("/find_users/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findUsers(@PathVariable("id") Long id) {
        LOGGER.info("Finding user by ID: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserDetails userDetails = new UserDetails();
            copyProperties(optionalUser.get(), userDetails);
            Set<String> roles = new HashSet<>();
            for (Role role : optionalUser.get().getRoles()) {
                roles.add(role.getName().name());
            }
            userDetails.setRoles(roles);
            LOGGER.info("User Details: {}", userDetails);
            return ResponseEntity.ok(userDetails);
        } else {
            LOGGER.error("User not found, User ID: {}", id);
            return ResponseEntity.badRequest()
                .body(new MessageResponse(false, "Error: user details not found"));
        }
    }


    /**
     * This method update user details
     * Only users with general role (ROLE_USER) can access this method
     * */
    @PostMapping("/update_users")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUsers(@Valid @RequestBody UserDetails userDetails) {
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            ResponseEntity<?> validationResponse = validatorService.validateUpdateUser(userDetails);
            if (!validationResponse.getStatusCode().equals(HttpStatus.OK)) {
                return validationResponse;
            }

            copyProperties(userDetails, optionalUser.get());
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse(true, "User details updated"));
        } else {
            LOGGER.error("Failed to update user details, User Details: {}", userDetails);
            return ResponseEntity.badRequest().body(new MessageResponse(false, "Invalid user ID"));
        }
    }


    /**
     * This method update user password
     * Only users with general role (ROLE_USER) can access this method
     * */
    @PostMapping("/change_password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(
        @Valid @RequestBody ChangePassRequest changePassRequest) {
        Optional<User> optionalUser = userRepository.findById(changePassRequest.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            ResponseEntity<?> validationResponse = validatorService.validatePassword(
                changePassRequest.getNewPassword(), user.getName(), user.getSurname(),
                changePassRequest.getConfirmPassword());

            if (!validationResponse.getStatusCode().equals(HttpStatus.OK)) {
                return validationResponse;
            }

            if (encoder.matches(changePassRequest.getOldPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(changePassRequest.getNewPassword()));
                userRepository.save(user);
                LOGGER.info("Password updated, User ID: {}", changePassRequest.getUserId());
                return ResponseEntity.ok()
                    .body(new MessageResponse(false, "Password updated successful"));
            } else {
                LOGGER.error("Invalid old password, User ID: {}", changePassRequest.getUserId());
                return ResponseEntity.badRequest()
                    .body(new MessageResponse(false, "Invalid old password"));
            }
        } else {
            LOGGER.error("User not found, User ID: {}", changePassRequest.getUserId());
            return ResponseEntity.badRequest().body(new MessageResponse(false, "Invalid user ID"));
        }
    }

}