package com.app;

import java.io.File;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	// @Value => annotation to inject value of SpEL expression into a field
	@Value("${file.upload.location}")
	private String folderName;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	// configure ModelMapper as a spring bean
	// equivalent to <bean> tag in xml file
	@Bean
	public ModelMapper mapper() {
		log.info("In model mappler ");
		return new ModelMapper();
	}

	// password encoder

	@Bean
	public PasswordEncoder encoder() {
		log.info("In Password Encoder ");
		return new BCryptPasswordEncoder();
	}

	// for imagehandling
	@Override
	public void run(String... args) throws Exception {
		System.out.println("in run " + folderName);
		// create images folder if it doesn't exist
		File dir = new File(folderName);
		if (!dir.exists()) {
			System.out.println("Created folder/s " + dir.mkdirs());
		} else
			System.out.println("folder alrdy exists");
	}

}
