package net.netosoft.oauth.monolite.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 43;
	
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;

	public Integer getUserId(){
		return userId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	@Override
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username = username;
	}

	public String getFirstName(){
		return firstName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	@Override
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	@Override
	public boolean isAccountNonExpired(){
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired){
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked(){
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked){
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired(){
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired){
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
		this.authorities = authorities;
	}
	
	@Override
	public String toString(){
		return new StringBuilder("AppUserDetails:{")
				.append("userId:").append(userId).append(", ")
				.append("username:").append(username).append(", ")
				.append("firstName:").append(firstName).append(", ")
				.append("lastName:").append(lastName).append(", ")
				.append("email:").append(email).append(", ")
				.append("accountNonExpired:").append(accountNonExpired).append(", ")
				.append("accountNonLocked:").append(accountNonLocked).append(", ")
				.append("credentialsNonExpired:").append(credentialsNonExpired).append(", ")
				.append("enabled:").append(enabled).append(", ")
				.append("authorities:").append(authorities).append("}")
				.toString();
	}
}
