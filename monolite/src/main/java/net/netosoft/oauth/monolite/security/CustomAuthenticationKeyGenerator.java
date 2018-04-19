package net.netosoft.oauth.monolite.security;

import net.netosoft.oauth.monolite.security.services.SecurityUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import net.netosoft.oauth.monolite.exceptions.AppException;
import net.netosoft.oauth.monolite.exceptions.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto
 */
@Component
public class CustomAuthenticationKeyGenerator
				extends DefaultAuthenticationKeyGenerator{

	private static final Logger LOGGER =
			LoggerFactory.getLogger(CustomAuthenticationKeyGenerator.class);
	
	private static final String CLIENT_ID = "client_id";

	private static final String SCOPE = "scope";

	private static final String USERNAME = "username";

	private static final String DEVICE_ID = "device_id";
	
	@Autowired
	private SecurityUtils securityUtils;

	@Override
	public String extractKey(OAuth2Authentication authentication){
		LOGGER.debug("Generating token key");
		Map<String, String> values = new LinkedHashMap<>();
		
		OAuth2Request request = authentication.getOAuth2Request();

		if(!authentication.isClientOnly()){
			values.put(USERNAME, authentication.getName());
		}

		values.put(CLIENT_ID, request.getClientId());

		if(request.getScope() != null){
			values.put(SCOPE, OAuth2Utils.formatParameterList(
					new TreeSet<>(request.getScope())));
		}

		String deviceId;
		try{
			// Get device id from header
			deviceId = securityUtils.getImei();
			values.put(DEVICE_ID, deviceId);
		}catch(AppException ex){
			throw new IllegalArgumentException(
					ErrorCodes.CIPHER_ERROR.description, ex);
		}
		
		return generateKey(values);
	}
}
