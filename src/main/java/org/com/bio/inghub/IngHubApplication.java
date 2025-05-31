package org.com.bio.inghub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IngHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngHubApplication.class, args);
    }

}
