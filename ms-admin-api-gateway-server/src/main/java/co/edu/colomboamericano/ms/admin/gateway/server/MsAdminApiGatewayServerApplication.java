package co.edu.colomboamericano.ms.admin.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsAdminApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAdminApiGatewayServerApplication.class, args);
	}

}
