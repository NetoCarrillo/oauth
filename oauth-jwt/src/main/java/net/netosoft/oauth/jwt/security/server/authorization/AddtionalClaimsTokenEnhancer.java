package net.netosoft.oauth.jwt.security.server.authorization;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto
 */
@Component
public class AddtionalClaimsTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
									 OAuth2Authentication auth){
		ResourceOwnerUserDetails user =
				(ResourceOwnerUserDetails)auth.getPrincipal();
		
		Map<String, Object> addtional = new HashMap<>();
		addtional.put("email", user.getEmail());
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)accessToken;
		token.setAdditionalInformation(addtional);
		
		return accessToken;
	}
	
}
