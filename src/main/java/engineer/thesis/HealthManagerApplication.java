package engineer.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("medcom.properties")
public class HealthManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthManagerApplication.class, args);
	}

}
