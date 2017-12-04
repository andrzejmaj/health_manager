package engineer.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@PropertySource("medcom.properties")
@ComponentScan({"engineer.thesis.core", "engineer.thesis.medcom"})
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HealthManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthManagerApplication.class, args);
	}

}
