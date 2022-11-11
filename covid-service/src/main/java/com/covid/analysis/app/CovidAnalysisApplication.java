
package com.covid.analysis.app;

import com.covid.analysis.app.model.entities.Role;
import com.covid.analysis.app.model.enums.ERole;
import com.covid.analysis.app.repository.entities.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class CovidAnalysisApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CovidAnalysisApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://*:4200");
				registry.addMapping("/**").allowedOrigins("http://*:4300");
			}
		};
	}

	@Bean
	public void createRoles() {

		if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
			Role admin = Role.builder()
					.name(ERole.ROLE_ADMIN)
					.build();
			roleRepository.save(admin);
			log.info(String.format("%s%s",admin.getName().name(), " is created"));
		}

		if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
			Role user = Role.builder()
					.name(ERole.ROLE_USER)
					.build();
			roleRepository.save(user);
			log.info(String.format("%s%s",user.getName().name(), " is created"));
		}

	}

}

