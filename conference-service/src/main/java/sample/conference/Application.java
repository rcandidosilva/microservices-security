package sample.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import static com.google.common.collect.Lists.*;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	SessionRepository repo;

	@Override
	public void run(String... args) throws Exception {
		repo.save(new Session("CON4333", "Protecting Java Microservices: Practices and Strategies", "Intermmediate", newArrayList(1L, 2L)));
		repo.save(new Session("CON5598", "12 Factors of Cloud Success", "Beginner", newArrayList(3L)));
		repo.save(new Session("KEY7383", "Developer Keynote", "Beginner", newArrayList(4L, 5L)));
		repo.save(new Session("CON4560", "Build, Test, and Deliver Code Faster with Containers", "Intermmediate", newArrayList(4L)));
		repo.save(new Session("CON7610", "Microservices Data Patterns: CQRS and Event Sourcing", "Advanced", newArrayList(3L)));		
	}
}