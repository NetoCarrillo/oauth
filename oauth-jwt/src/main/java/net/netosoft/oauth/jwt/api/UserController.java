package net.netosoft.oauth.jwt.api;

import net.netosoft.oauth.jwt.api.dto.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ernesto
 */
@RestController
public class UserController{
	@GetMapping("/api/profile")
	public ResponseEntity<UserProfile> myProfile(){
		String username = (String)SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		String email = username  + "@mailinator.com";
		UserProfile profile = new UserProfile(username, email);
		
		return ResponseEntity.ok(profile);
	}
}
