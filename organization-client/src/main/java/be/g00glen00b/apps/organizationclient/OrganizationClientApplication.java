package be.g00glen00b.apps.organizationclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OrganizationClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationClientApplication.class, args);
    }

    /**
     * We can define a <code>WebClient</code> with a generic base URL to use it for all organization API calls.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080/api");
    }

}
