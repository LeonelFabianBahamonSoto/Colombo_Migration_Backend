package co.edu.colomboamericano.caelassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsColomboCaelAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsColomboCaelAssessmentApplication.class, args);
	}

}
