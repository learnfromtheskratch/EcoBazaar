package com.example.userlogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class UserloginApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8082");
		SpringApplication.run(UserloginApplication.class, args);

	}

}

