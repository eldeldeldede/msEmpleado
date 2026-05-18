package cl.duoc.msempleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsempleadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsempleadoApplication.class, args);
	}

}
