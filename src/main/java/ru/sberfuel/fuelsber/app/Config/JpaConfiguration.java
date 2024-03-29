package ru.sberfuel.fuelsber.app.Config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.sberfuel.fuelsber.app.*")
@EntityScan("ru.sberfuel.fuelsber.app.entity")
public class JpaConfiguration {
}
