package net.netosoft.oauth.jwt.api.dto;

import java.io.Serializable;

/**
 *
 * @author ernesto
 */
public class UserProfile implements Serializable{
	
	private static final long serialVersionUID = 124654L;
	
	private String name;
	private String email;

	public UserProfile(String name, String email){
		this.name = name;
		this.email = email;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}
}
