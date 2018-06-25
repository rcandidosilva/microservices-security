package sample.speaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	SpeakerRepository repo;

	@Override
	public void run(String... args) throws Exception {
		repo.save(new Speaker(1L, "Rodrigo", "Silva", "rcandidosilva@gmail.com", "JUGLeader", "GUJavaSC" ));
		repo.save(new Speaker(2L, "Alessandro", "Ribeiro", "alessandro.ribeiro@integritas.net", "CTO", "Integritas"));
		repo.save(new Speaker(3L, "Edson", "Yanaga", "edson@email.com", "Director of Developer Experience", "RedHat"));
		repo.save(new Speaker(4L, "Bruno", "Souza", "bruno@email.com", "Programmer/Developer", "ToolsCloud"));
		repo.save(new Speaker(5L, "Bruno", "Borges", "bruno.borges@oracle.com", "Developer Engagement", "Oracle"));
	}
		
}