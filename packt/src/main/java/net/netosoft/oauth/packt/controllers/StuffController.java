package net.netosoft.oauth.packt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ernesto
 */
@RestController
//@RequestMapping("stuff")
public class StuffController{
	
	@GetMapping(path = {"/message"})
	public ResponseEntity<String> userInfo(){
		return ResponseEntity.ok("Hey!");
	}
}
