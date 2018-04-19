package net.netosoft.oauth.monolite.security;

import net.netosoft.oauth.monolite.security.services.SecurityUtils;
import net.netosoft.oauth.monolite.security.services.UserAuthenticationService;
import net.netosoft.oauth.monolite.crypto.DataCipher;
import net.netosoft.oauth.monolite.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DomainAuthenticationProvider implements AuthenticationProvider{
	private static final Logger LOGGER =
			LoggerFactory.getLogger(DomainAuthenticationProvider.class);
	
	private static final String CANT_DECRYPT_ERROR = "Can't decrypt credentials";
	
	@Autowired
	private UserAuthenticationService authService;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	@Autowired
	private DataCipher cipher;
	
	/**
	 * 
	 * @param authentication
	 * @return 
	 */
	@Override
	public Authentication authenticate(Authentication authentication){
		String[] arr;
		try{
			arr = cipher.decrypt(authentication.getName()).split(":");
		}catch(AppException ex){
			throw new BadCredentialsException(CANT_DECRYPT_ERROR, ex);
		}

		final String username = arr[0];
		LOGGER.debug("Authenticating user {}", username);
		
		//Validate User
		Authentication auth = localAuth(authentication, username);
		
		Integer userId = ((AppUserDetails)auth.getPrincipal()).getUserId();
		
		//Validate user unique session
		String deviceId = logSuccessfulLogin(userId);
		
		LOGGER.info("User {} successful login from device {}", userId, deviceId);
		
		return auth;
	}
	
	private String logSuccessfulLogin(Integer userId){
		
		String deviceId;
		try{
			// Get device id from header
			deviceId = securityUtils.getImei();
			LOGGER.debug("Authenticate device id: {}", deviceId);
		}catch(AppException ex){
			throw new BadCredentialsException(ex.getMessage(), ex);
		}
		
		authService.setLastSuccessfulLogin(userId);
		
		return deviceId;
	}
	
	@Override
	public boolean supports(Class<?> klass){
		return true;
	}
	
	private Authentication localAuth(Authentication authentication,
									 String username){
		String password;
		try{
			password = cipher.decrypt((String)authentication.getCredentials());
		}catch(AppException ex){
			throw new BadCredentialsException(CANT_DECRYPT_ERROR, ex);
		}
		return authService.authenticateUser(username, password);
	}
}
