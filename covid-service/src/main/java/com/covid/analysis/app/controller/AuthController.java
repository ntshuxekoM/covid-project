package com.covid.analysis.app.controller;

import com.covid.analysis.app.payload.ForgotPassRequest;
import com.covid.analysis.app.security.services.impl.UserDetailsImpl;
import com.covid.analysis.app.model.entities.EmailContent;
import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.entities.User;
import com.covid.analysis.app.model.enums.ERole;
import com.covid.analysis.app.payload.JwtResponse;
import com.covid.analysis.app.payload.LoginRequest;
import com.covid.analysis.app.payload.MessageResponse;
import com.covid.analysis.app.payload.SignupRequest;
import com.covid.analysis.app.repository.entities.EmailContentRepository;
import com.covid.analysis.app.repository.entities.RoleRepository;
import com.covid.analysis.app.repository.entities.UserRepository;
import com.covid.analysis.app.security.jwt.JwtUtils;
import com.covid.analysis.app.utils.Constant;
import com.covid.analysis.app.validator.ValidatorService;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailContentRepository emailContentRepository;
    @Autowired
    private ValidatorService validatorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    /**
     * Login/signin rest service
     * If the login details are valid, this service will return a token
     * together with other details such as user ID, ect that are related to the login user
     * */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        LOGGER.info("JWT: {}", jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles,userDetails.getFullName()));
    }

    /**
     * Rest service to create user profile
     * By default we assign general role (ROLE_USER) to the user
     * */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        ResponseEntity<?> validationResponse = validatorService.validateRegisterUser(signUpRequest);
        if (!validationResponse.getStatusCode().equals(HttpStatus.OK)) {
            return validationResponse;
        }

        // Create new user's account
        User user = new User();
        user.setIdNumber(signUpRequest.getIdNumber());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setCellNumber(signUpRequest.getCellNumber());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = new HashSet<>();
        strRoles.add("user");//Adding default user role
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        saveEmailContent(user,signUpRequest.getPassword());

        return ResponseEntity.ok(new MessageResponse(true, "User registered successfully!"));
    }


    /**
     * Rest service to reset user password
     * If the email address is valid(linked to an account), the service will generate a
     * random password and send it to the user via an email
     * */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassRequest request) {
        String email = request.getEmail();
        LOGGER.info("Forgot password, email: {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String newPassword = generatePassword();
            user.setPassword(encoder.encode(newPassword));
            saveForgotPassEmailContent(user, newPassword);
            userRepository.save(user);
            return ResponseEntity.ok()
                .body(new MessageResponse(true,
                    "Your password has been updated and sent to your email"));
        } else {
            LOGGER.error("User not found, User email: {}", email);
            return ResponseEntity.badRequest()
                .body(new MessageResponse(false,
                    "Error: No registered user with the email provided"));
        }
    }

    /**
     * A method to generate a random password
     * */
    public String generatePassword() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                (random.nextFloat() * (rightLimit - leftLimit + 1));
            Random rand = new Random();
            int n = rand.nextInt(15) + 1;
            if (n > 10) {
                buffer.append(Character.toUpperCase((char) randomLimitedInt));
            } else {
                buffer.append((char) randomLimitedInt);
            }
        }
        String generatedString = buffer.toString();
        generatedString = generatedString.trim();
        System.out.println(generatedString);
        LOGGER.info("********************** {} *********************",generatedString);
        return generatedString;
    }

    /**
     * This method save forgot password email content in the database
     * The saved record will be retrieved by the timer (see @ScheduledTasks)
     * and processed, and an email will be sent if the email address is valid
     * */
    private void saveForgotPassEmailContent(User user, String password) {
        try {
            LOGGER.info(
                "Saving forgot password email content for [Name: {}, Surname: {}, email: {}]",
                user.getName(), user.getSurname(), user.getEmail());

            String welcome = "<p>Hi #NAME#,</p>"
                + "<br/>"
                + "<p>Your password has been updated, below please find your new login details.</p>"
                + "<br/>"
                + "<p><b>User Name: </b>  " + user.getEmail() + "</p>"
                + "<p><b>Password: </b>  " + password + "</p>"
                + "<p>Our Best</p>"
                + "<p>COVID-19 Data Analysis Team</p>"
                + "<br/>";
            welcome = welcome.replaceAll("#NAME#", user.getName() + " " + user.getSurname());
            EmailContent emailContent = new EmailContent();
            emailContent.setFromEmail(Constant.APP_EMAIL);
            emailContent.setToEmail(user.getEmail());
            emailContent.setSubject("COVID-19 Data Analysis Forgot Password");
            emailContent.setContentMssg(welcome);
            emailContent.setRetryCount(0);
            emailContent.setRunDate(new Date());
            emailContent.setCreateUser(user);
            emailContent.setLastUpdatedUser(user);
            emailContentRepository.save(emailContent);
        } catch (Exception e) {
            LOGGER.error("Error when saving forgot password email email: ", e);
        }
    }

    /**
     * This method save "registration" email content in the database
     * The saved record will be retrieved by the timer (see @ScheduledTasks)
     * and processed, and an email will be sent if the email address is valid
     * */
    private void saveEmailContent(User user, String password) {
        try {
            LOGGER.info("Saving email content for [Name: {}, Surname: {}, email: {}]", user.getName(), user.getSurname(), user.getEmail());

            String welcome = "<p>Hi #NAME#,</p>"
                    + "<br/>"
                    + "<p>Your account is created successfully, below please find your login details.</p>"
                    + "<br/>"
                    + "<p><b>User Name: </b>  "+user.getEmail()+"</p>"
                    + "<p><b>Password: </b>  "+password+"</p>"
                    + "<p>Thank you for joining us</p>"
                    + "<p>Our Best</p>"
                    + "<p>COVID-19 Data Analysis Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", user.getName() + " " + user.getSurname());
            EmailContent emailContent = new EmailContent();
            emailContent.setFromEmail(Constant.APP_EMAIL);
            emailContent.setToEmail(user.getEmail());
            emailContent.setSubject("COVID-19 Data Analysis Registration");
            emailContent.setContentMssg(welcome);
            emailContent.setRetryCount(0);
            emailContent.setRunDate(new Date());
            emailContent.setCreateUser(user);
            emailContent.setLastUpdatedUser(user);
            emailContentRepository.save(emailContent);
        } catch (Exception e) {
            LOGGER.error("Error when saving registration email email: ", e);
        }
    }


}