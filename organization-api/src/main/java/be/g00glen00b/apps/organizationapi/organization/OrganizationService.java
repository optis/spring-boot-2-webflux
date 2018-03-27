package be.g00glen00b.apps.organizationapi.organization;

import be.g00glen00b.apps.organizationapi.twitter.Tweet;
import be.g00glen00b.apps.organizationapi.twitter.TwitterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Using <code>@AllArgConstructor</code> to allow Spring to inject the repository.
 */
@Service
@AllArgsConstructor
public class OrganizationService {
	private OrganizationRepository repository;
	private TwitterService twitterService;

	/**
	 * Method to find all organizations.
	 * @return Flux of <code>Organization</code>. Which means it can return zero to many, including infinite results.
	 */
	public Flux<Organization> findAll() {
		return repository.findAll();
	}

	/**
	 * Method to save an organization
	 * @param organization The input itself is reactive as well. This means we can't just call <code>repository.save()</code>
	 *                     but we have to use the <code>flatMap</code> to flatten out the Mono of the repository call.
	 * @return Mono of <code>Organization</code>. Which means it can return zero or one results before completing.
	 */
	public Mono<Organization> save(Mono<Organization> organization) {
		return organization.flatMap(repository::save);
	}

	/**
	 * Method to find tweets related to the technologies of an <code>Organization</code>.
	 * @return Returns an infinite <code>Flux</code> of <code>Tweet</code>. This is done by using the <code>map</code>
	 * operator to find a list of all hashtags, followed by <code>flatMapMany</code> to map from a <code>Mono</code> to
	 * a <code>Flux</code> and to flatten out nested publishers.
	 */
	public Flux<Tweet> findTweets(String organizationName) {
		return repository
			.findById(organizationName)
			.map(Organization::getHashtags)
			.flatMapMany(twitterService::findTweets);
	}
}
