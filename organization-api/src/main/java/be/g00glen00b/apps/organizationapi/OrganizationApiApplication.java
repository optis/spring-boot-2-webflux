package be.g00glen00b.apps.organizationapi;

import be.g00glen00b.apps.organizationapi.organization.OrganizationHandler;
import be.g00glen00b.apps.organizationapi.twitter.TwitterAPIConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * The <code>@EnableConfigurationProperties</code> annotation can be used to tell Spring boot which properties classes
 * there are. Otherwise they're not picked up.
 */
@SpringBootApplication
@EnableConfigurationProperties(TwitterAPIConfigurationProperties.class)
public class OrganizationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationApiApplication.class, args);
    }

    /**
     * Router function can bu used to define routes for the entire application.
     *
     * @param organizationHandler All handlers should be included.
     * @return A <code>RouterFunction</code> using the <code>route()</code> static function.
     */
    @Bean
    public RouterFunction<?> routerFunction(OrganizationHandler organizationHandler) {
        return route(GET("/api/organization"), organizationHandler::findAll)
            .and(route(POST("/api/organization"), organizationHandler::save))
            .and(route(GET("/api/organization/{name}/tweets"), organizationHandler::findTweets));
    }

    /**
     * Configuration of Twitter4J including authentication details (OAuth tokens)
     */
    @Bean
    public Configuration configuration(TwitterAPIConfigurationProperties properties) {
        return new ConfigurationBuilder()
            .setOAuthConsumerKey(properties.getConsumerKey())
            .setOAuthConsumerSecret(properties.getConsumerSecret())
            .setOAuthAccessToken(properties.getAccessToken())
            .setOAuthAccessTokenSecret(properties.getAccessTokenSecret())
            .build();
    }
}
