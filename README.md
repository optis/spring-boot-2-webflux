# Spring boot 2

## Libraries

This project uses Spring boot 2, Spring WebFlux, Reactive MongoDB, Lombok and Twitter4J.

## Configuration

To get this project up and running, you need to configure the following environment variables or application properties:

- `TWITTER_CONSUMER_KEY`
- `TWITTER_CONSUMER_SECRET`
- `TWITTER_ACCESS_TOKEN`
- `TWITTER_ACCESS_TOKEN_SECRET`

These details can be generated at [Twitter Application Management](https://apps.twitter.com/).

## Resources

- [Presentation](https://docs.google.com/presentation/d/16pJS61iokf-yh4eGyY8XQkO51q9tHK2uzTuCjqnWVf4/edit?usp=sharing)
- [Blogpost about getting started with Spring boot 2](https://g00glen00b.be/getting-started-spring-boot-2/)
- [Blogpost containing conference talks about Spring boot 2](https://blog.optis.be/spring-devoxx-7c0fa8a9dc9f)

## Snippets from presentation

### Key components

```java
// Flux<Integer> extends Publisher<Integer>
Flux.range(0, 1000)
    // Operators
    .filter(nr -> nr % 2 == 0)
    .map(nr -> nr * 3)
    // Subscriber
    .subscribe(System.out::println);
```

### Webflux

```java
@RestController
@RequestMapping("/api/person")
public class PersonController {
    @GetMapping
    public Flux<Person> findAll() { // Returns Flux<Person>
        return Flux.just(
        	new Person("John", "Doe"),
	        new Person("Jane", "Doe"));
    }
}
```

### Router function

```java
@Bean
public RouterFunction<?> routes() {
    return route(GET("/api/person"), request -> ServerResponse
        .ok()
        .body(Flux.just(
        	new Person("John", "Doe"),
        	new Person("Jane", "Doe")), Person.class));
}
```

### Reactive repository

```java
public interface PersonRepository extends ReactiveCrudRepository<Person, String> {
    
}
```

### WebClient

```java
WebClient
	// Base path
	.create("http://localhost:8080/api")
    .get()
    .uri(uriBuilder -> uriBuilder
    	.path("/person")
        .queryParam("offset", 0)
        .queryParam("limit", 10)
        .build())
    .retrieve()
    // Returns Flux<Person>
    .bodyToFlux(Person.class);
```

