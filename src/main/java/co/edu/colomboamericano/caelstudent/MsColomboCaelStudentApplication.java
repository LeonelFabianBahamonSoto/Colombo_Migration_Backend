package co.edu.colomboamericano.caelstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsColomboCaelStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsColomboCaelStudentApplication.class, args);
	}

}
