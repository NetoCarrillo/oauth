package com.example;

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

	public AuthorizationCodeResourceDetails getClient(){
		return client;
	}

	public ResourceServerProperties getResource(){
		return resource;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(512);
		sb.append("{\n");
		
		sb.append("\tclient:{\n");
		sb.append("\t\tAccessTokenUri: ")
				.append(client.getAccessTokenUri()).append(",\n");
		sb.append("\t\tClientId: ")
				.append(client.getClientId()).append(",\n");
		sb.append("\t\tClientSecret: ")
				.append(client.getClientSecret()).append(",\n");
		sb.append("\t\tGrantType: ")
				.append(client.getGrantType()).append(",\n");
		sb.append("\t\tId: ")
				.append(client.getId()).append(",\n");
		sb.append("\t\tPreEstablishedRedirectUri: ")
				.append(client.getPreEstablishedRedirectUri()).append(",\n");
		sb.append("\t\tTokenName: ")
				.append(client.getTokenName()).append(",\n");
		sb.append("\t\tUserAuthorizationUri: ")
				.append(client.getUserAuthorizationUri()).append(",\n");
		sb.append("\t\tScope: [\n");
		if(client.getScope() != null){
			for(String s : client.getScope()){
				sb.append("\t\t\t").append(s).append(",\n");
			}
		}
		sb.append("\t\t]\n");
		sb.append("\t}\n");
		
		sb.append("\tresource:{\n");
		sb.append("\t\tClientId: ")
				.append(resource.getClientId()).append(",\n");
		sb.append("\t\tClientSecret: ")
				.append(resource.getClientSecret()).append(",\n");
		sb.append("\t\tFilterOrder: ")
				.append(resource.getFilterOrder()).append(",\n");
		sb.append("\t\tId: ")
				.append(resource.getId()).append(",\n");
		sb.append("\t\tResourceId: ")
				.append(resource.getResourceId()).append(",\n");
		sb.append("\t\tServiceId: ")
				.append(resource.getServiceId()).append(",\n");
		sb.append("\t\tTokenInfoUri: ")
				.append(resource.getTokenInfoUri()).append(",\n");
		sb.append("\t\tTokenType: ")
				.append(resource.getTokenType()).append(",\n");
		sb.append("\t\tUserInfoUri: ")
				.append(resource.getUserInfoUri()).append(",\n");
		sb.append("\t}\n");
		
		sb.append('}');
		
		return sb.toString();
	}
}
