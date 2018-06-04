package net.netosoft.oauth.jwt.security.server.authorization;

import net.netosoft.oauth.jwt.persistence.model.ResourceOwner;
import net.netosoft.oauth.jwt.persistence.repositories.ResourceOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ernesto
 */
@Service
public class ResourceOwnerDetailsService implements UserDetailsService{
	
	@Autowired
	private ResourceOwnerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException{
		
		ResourceOwner user = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
		
		return new ResourceOwnerUserDetails(user);
	}
	
}
