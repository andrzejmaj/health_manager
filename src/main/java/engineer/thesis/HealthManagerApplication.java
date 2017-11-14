package engineer.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource("medcom.properties")
@ComponentScan({"engineer.thesis.core", "engineer.thesis.medcom"})
@EnableScheduling
public class HealthManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthManagerApplication.class, args);
	}

}
