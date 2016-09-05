package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Petclinic2Application {

	public static void main(String[] args) {
	    log.info("++++ start application petClinic boot demo...");
		SpringApplication.run(Petclinic2Application.class, args);
		
	}
}
