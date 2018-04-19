package net.netosoft.oauth.monolite.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ernesto
 */
@RestController
@RequestMapping("user/info")
public class UserInfoController{
	
	@GetMapping(path = {"/", "basic"})
	public ResponseEntity<String> userInfo(){
		
		return ResponseEntity.ok("Usuario X");
	}
}
