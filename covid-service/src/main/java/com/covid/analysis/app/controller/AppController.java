package com.covid.analysis.app.controller;

import com.covid.analysis.app.repository.entities.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private RoleRepository roleRepository;

}
