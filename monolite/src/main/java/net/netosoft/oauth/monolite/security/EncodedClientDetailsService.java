package net.netosoft.oauth.monolite.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto
 */
@Component
public class EncodedClientDetailsService implements ClientDetailsService{

	private final Map<String, ClientDetails> map;

	public EncodedClientDetailsService(){
		map = new HashMap<>();
	}
	
	@Override
	public ClientDetails loadClientByClientId(String clientId){

		ClientDetails details = map.get(clientId);
		
		if(details == null){
			throw new ClientRegistrationException(new StringBuilder("Client [")
					.append(clientId).append("]doesn't exists").toString());
		}
		
		return details;
	}
	
	public void addClientDetails(ClientDetails details){
		map.put(details.getClientId(), details);
	}
}
