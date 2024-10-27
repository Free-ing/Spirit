package service.spirit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
//@Import(OpenAiAutoConfiguration.class)
public class SpiritApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiritApplication.class, args);
    }

}
