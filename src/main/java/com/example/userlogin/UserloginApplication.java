package com.example.userlogin;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserloginApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("server.port", dotenv.get("SERVER_PORT"));
		System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
		System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
		System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
		System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
		System.setProperty("jwt.expiration", dotenv.get("JWT_EXPIRATION"));
		SpringApplication.run(UserloginApplication.class, args);

	}

}
