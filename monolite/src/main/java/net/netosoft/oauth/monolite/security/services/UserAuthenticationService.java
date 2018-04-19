package net.netosoft.oauth.monolite.security.services;

import org.springframework.security.core.Authentication;

public interface UserAuthenticationService{

	/**
	 * Validate user's info against the info stored in the data source. If user
	 * is valid an object that contains roles descriptions will be returned.
	 * Otherwise AuthenticateException will be thrown.
	 * 
	 * @param userId User id
	 * @param credentials User's credentials, usually the password.
	 * @return <code>true</code> if the user is valid, <code>false</code> if not found, invalid password, etc.
	 */
	Authentication authenticateUser(String userId, String credentials);
	
	void setLastLoginAttempt(Integer userId);
	
	void setLastSuccessfulLogin(Integer userId);
	
	void setLastSuccessfulLogin(Integer userId, String device);
}
