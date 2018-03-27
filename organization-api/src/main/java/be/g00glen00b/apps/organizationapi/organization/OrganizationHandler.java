package be.g00glen00b.apps.organizationapi.organization;

import be.g00glen00b.apps.organizationapi.twitter.Tweet;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Using <code>@AllArgConstructor</code> to allow Spring to inject the service.
 */
@Component
@AllArgsConstructor
public class OrganizationHandler {
	private OrganizationService service;

	/**
	 * Handler for the <code>GET /api/organization</code> operation.
	 */
	public Mono<ServerResponse> findAll(ServerRequest request) {
	    return ServerResponse
	        .ok()
			.body(service.findAll(), Organization.class);
	}

	/**
	 * Handler for the <code>POST /api/organization</code> operation. It uses the <code>ServerRequest</code> parameter
	 * to obtain the request body.
	 */
	public Mono<ServerResponse> save(ServerRequest request) {
	    return ServerResponse
	        .ok()
			.body(service.save(request.bodyToMono(Organization.class)), Organization.class);
	}

	/**
	 * Handler for the <code>GET /api/organization/{name}/tweets</code> operation. It uses the <code>ServerRequest</code>
	 * parameter to obtain the path variable containing the name of the organization. By default, a <code>Flux</code> is
	 * converted to a JSON array. But since we have an infinite amount of tweets, we use a stream of JSON objects rather
	 * than an array, and send them using server-sent events (SSE).
	 */
	public Mono<ServerResponse> findTweets(ServerRequest request) {
	    return ServerResponse
	        .ok()
			.contentType(MediaType.APPLICATION_STREAM_JSON)
			.body(service.findTweets(request.pathVariable("name")), Tweet.class);
	}
}
