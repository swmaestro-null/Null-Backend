package swmaestronull.nullbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;

@SpringBootApplication(exclude = {ContextInstanceDataAutoConfiguration.class})
public class NullBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NullBackendApplication.class, args);
	}

}
