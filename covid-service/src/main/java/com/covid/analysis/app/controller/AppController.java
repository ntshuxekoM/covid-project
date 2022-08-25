package com.covid.analysis.app.controller;

import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.enums.ERole;
import com.covid.analysis.app.repository.entities.RoleRepository;
import com.covid.analysis.app.repository.entities.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/phishing-detector")
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/test/hello/v1")
    public String hello() {

        Role role1 = new Role();
        role1.setName(ERole.ROLE_USER);
        roleRepository.saveAndFlush(role1);

        role1 = new Role();
        role1.setName(ERole.ROLE_ADMIN);
        roleRepository.saveAndFlush(role1);

        return "Ya runner!!";
    }

}
