package org.example.restaurants;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantsApplication {

	public static void main(String[] args) {
//		Dotenv.configure().systemProperties().load();
		SpringApplication.run(RestaurantsApplication.class, args);
	}

}
