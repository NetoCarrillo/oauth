package net.netosoft.oauth.monolite.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 *
 * @author ernesto
 */
public class ClientResources{
	@NestedConfigurationProperty
	private AuthorizationCodeResourceDetails client =
			new AuthorizationCodeResourceDetails();

	@NestedConfigurationProperty
	private ResourceServerProperties resource = new ResourceServerProperties();

	public AuthorizationCodeResourceDetails getClient() {
		return client;
	}

	public ResourceServerProperties getResource() {
		return resource;
	}

	@Override
	public String toString(){
		return new StringBuilder()
				.append("ClientResources: {")
				.append("client: ").append(client).append(", ")
				.append("resource: ").append(resource)
				.append('}')
				.toString();
	}
}
