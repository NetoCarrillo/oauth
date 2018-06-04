package net.netosoft.oauth.jwt.security.server.authorization;

import java.util.Collection;
import java.util.Collections;
import net.netosoft.oauth.jwt.persistence.model.ResourceOwner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ernesto
 */
public class ResourceOwnerUserDetails implements UserDetails{
	private static final long serialVersionUID = 14257L;
	
	private final ResourceOwner wrapped;

	public ResourceOwnerUserDetails(ResourceOwner wrapped){
		this.wrapped = wrapped;
	}
	
	@Override
	public String getUsername(){
		return wrapped.getUsername();
	}

	@Override
	public String getPassword(){
		return wrapped.getPassword();
	}

	public String getEmail(){
		return wrapped.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.emptyList();
	}

	@Override
	public boolean isAccountNonExpired(){
		return true;
	}

	@Override
	public boolean isAccountNonLocked(){
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired(){
		return true;
	}

	@Override
	public boolean isEnabled(){
		return true;
	}

}
