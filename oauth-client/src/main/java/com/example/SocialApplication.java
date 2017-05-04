package com.example;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SocialApplication{

	@RequestMapping({"/user", "/me"})
	public Map<String, String> user(Principal principal){
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		
		return map;
	}
	
	@RequestMapping("/unauthenticated")
	public String unauthenticated(){
		return "redirect:/?error=true";
	}

	public static void main(String[] args){
		SpringApplication.run(SocialApplication.class, args);
	}
	
}
