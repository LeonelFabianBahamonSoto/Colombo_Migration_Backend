package co.edu.colomboamericano.ms.admin.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsAdminConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAdminConfigServerApplication.class, args);
	}

}
