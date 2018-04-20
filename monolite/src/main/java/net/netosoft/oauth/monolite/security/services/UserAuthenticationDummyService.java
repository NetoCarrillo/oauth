package net.netosoft.oauth.monolite.security.services;

import java.util.ArrayList;
import java.util.List;
import net.netosoft.oauth.monolite.security.AppUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ernesto
 */
@Service
public class UserAuthenticationDummyService implements UserAuthenticationService{
	
	private static final Logger LOGGER =
			LoggerFactory.getLogger(UserAuthenticationDummyService.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication authenticateUser(String username, String credentials){
		
		Integer userId = 1;
		
		setLastLoginAttempt(userId);

		Authentication auth;
		if(validPassword(userId, credentials)){
			auth = buildAuthentication(userId, credentials);
		}else{
			throw new AccessDeniedException("Access denied");
		}
		
		return auth;
	}
	
	private Authentication buildAuthentication(Integer userId, String password){
		AppUserDetails det = getUserInfo(userId);
		return new UsernamePasswordAuthenticationToken(
				det, password, det.getAuthorities());
	}
	
	private AppUserDetails getUserInfo(Integer userId){
		AppUserDetails details = new AppUserDetails();
		
		details.setUserId(userId);
		details.setUsername("puchunguis");
		details.setFirstName("firstname");
		details.setLastName("lastname");
		details.setEmail("somemail@someserver.com");
		details.setEnabled(true);
		details.setAccountNonExpired(true);
		details.setAccountNonLocked(true);
		details.setCredentialsNonExpired(true);
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("user"));
		
		details.setAuthorities(authorities);
		
		return details;
	}

	/**
	 * Validate user's password against the one contained on the request.
	 * @param user User info retrieved from the data source.
	 * @param request
	 * @return <code>true</code> if the password is valid,
	 *         <code>false</code> otherwise.
	 */
	private boolean validPassword(Integer userId, String password){
		return true;
	}

	@Override
	@Transactional
	public void setLastLoginAttempt(Integer userId){
		//debug
		LOGGER.debug("Setting last attempt {}", userId);
		//debug
	}

	@Override
	public void setLastSuccessfulLogin(Integer userId){
		setLastSuccessfulLogin(userId, null);
	}

	@Override
	@Transactional
	public void setLastSuccessfulLogin(Integer userId, String device){
		//debug
		LOGGER.debug("Setting last login {} {}", userId, device);
		//debug
	}
}
