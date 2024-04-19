package ca.saultcollege.Lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ca.saultcollege.Lab3.data.AccessToken;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Lab3PrajwolGcApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext app = SpringApplication.run(Lab3PrajwolGcApplication.class, args);
	}
	@Bean
	public AccessToken getAccessToken(){return new AccessToken();};

}

