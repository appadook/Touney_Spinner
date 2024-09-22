package com.tourney_spinner.backend_tourney_spinner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.repository", "com.model"}) // Include the model package
@EntityScan("com.model") // Ensure this line is present
public class BackendTourneySpinnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendTourneySpinnerApplication.class, args);
    }

}
