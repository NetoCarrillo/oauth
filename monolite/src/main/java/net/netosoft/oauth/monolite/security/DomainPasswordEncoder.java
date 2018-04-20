package net.netosoft.oauth.monolite.security;

import java.util.Objects;
import net.netosoft.oauth.monolite.crypto.DataCipher;
import net.netosoft.oauth.monolite.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto
 */
@Component
public class DomainPasswordEncoder implements PasswordEncoder{

	@Autowired
	private DataCipher cipher;
	
	/**
	 * 
	 * @param rawPassword
	 * @return 
	 * @throws RuntimeException this wrapper of EspaciaException is necessary
	 * due to super method sign.
	 */
	@Override
	public String encode(CharSequence rawPassword){
		String msg;
		try{
			msg = cipher.encrypt(rawPassword.toString());
		}catch(AppException ex){
			throw new RuntimeException(ex);
		}
		return msg;
	}

	/**
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @throws RuntimeException this wrapper of EspaciaException is necessary
	 * due to super method sign.
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword){
		String decoded;
		try{
			decoded = cipher.decrypt(rawPassword.toString());
		}catch(AppException ex){
			throw new RuntimeException(ex);
		}
		
		return Objects.equals(decoded, encodedPassword);
	}
}