package net.netosoft.oauth.packt.dto;

/**
 *
 * @author ernesto
 */
public class UserProfile{
	private String name;
	private String email;

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